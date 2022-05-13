package org.nc.core.service;

import com.sun.mail.iap.ByteArray;
import org.nc.core.entity.ColumnEntity;
import org.nc.core.entity.TypeEnum;
import org.nc.core.repository.ColumnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class ColumnEntityService {

    private final ColumnRepository columnRepository;

    public ColumnEntityService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    
    public Mono<List<String>> saveColumn(List<TypeEnum> typeList) {
        AtomicInteger counter = new AtomicInteger(0);
        List<ColumnEntity> columnList = typeList
                .stream()
                .map(type -> {
                    ColumnEntity column = null;
                    switch (type) {
                        case STRING:
                            column = new ColumnEntity<String>(TypeEnum.STRING);
                        case DATE:
                            column = new ColumnEntity<Instant>(TypeEnum.DATE);
                        case FILE:
                            column = new ColumnEntity<ByteArray>(TypeEnum.FILE);
                        case LINK:
                            column = new ColumnEntity<String>(TypeEnum.STRING);
                    }
                    column.order = counter.getAndIncrement();
                    return column;
                }).collect(Collectors.toList());

        return columnRepository.saveAll(columnList).map(item -> item.id).collect(Collectors.toList());
    }

    
    public Mono<ColumnEntity> findColumnEntityById(String columnEntityId) {
        return columnRepository.findById(columnEntityId);
    }

    
    public void updateColumnWithNoteId(List<String> columnsIdList, String noteId) {
        columnsIdList.parallelStream().peek(columnId -> {
            Mono<ColumnEntity> columnEntity = columnRepository.findById(columnId);
            assert columnEntity != null;
            columnEntity.doOnSuccess(columnEntity1 -> {
                columnEntity1.noteId = noteId;
                columnRepository.save(columnEntity1);
            });
        });
    }

    
    public void deleteById(String noteId, String columnEntityId) {
        Mono<ColumnEntity> columnEntity = columnRepository.findById(columnEntityId);
        Mono<List<ColumnEntity>> columnEntities = columnRepository.findAllByNoteId(noteId);
        columnEntities.flatMap(list ->list.sort(Comparator.comparing(column -> column.order)));
        columnEntities.remove(columnEntity);
        columnEntities = columnEntities.stream().peek(listColumnEntity -> {
                    if (listColumnEntity.order >= columnEntity.order) --listColumnEntity.order;
                }
        ).collect(Collectors.toList());
        columnRepository.deleteById(columnEntityId);
        columnRepository.saveAll(columnEntities);
    }


    
    public String addData(String columnEntityId, Object data){
        try {
            ColumnEntity<Object> columnEntity = columnRepository.findById(columnEntityId).orElseThrow();
            columnEntity.data.add(data);
            columnEntity = columnRepository.save(columnEntity);
            return columnEntity.id;
        }
        catch (ClassCastException ex){
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    
    public String updateData(Integer index, Object newData, String columnId){
        ColumnEntity columnEntity = columnRepository.findById(columnId).orElseThrow();
        columnEntity.data.set(index, newData);
        return columnRepository.save(columnEntity).id;
    }

    
    public List<ColumnEntity> getAllColumnsByNoteId(String noteId){
        return columnRepository
                .findAllByNoteId(noteId)
                .stream()
                .sorted(Comparator.comparing(column -> column.order))
                .collect(Collectors.toList());
    }
}
