package org.nc.core.service;

import org.nc.core.entity.ColumnEntity;
import org.nc.core.entity.NoteEntity;
import org.nc.core.entity.TypeEnum;
import org.nc.core.repository.ColumnRepository;
import org.nc.core.repository.NoteEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class NoteEntityService {
    private final NoteEntityRepository noteEntityRepository;
    private final ColumnRepository columnRepository;
    private final ColumnEntityService columnEntityService;

    public NoteEntityService(NoteEntityRepository noteEntityRepository, ColumnRepository columnRepository, ColumnEntityService columnEntityService) {
        this.noteEntityRepository = noteEntityRepository;
        this.columnRepository = columnRepository;
        this.columnEntityService = columnEntityService;
    }

    
    public Mono<String> saveNote(List<TypeEnum> typeList){
        LinkedList<String> columnList = (LinkedList<String>) columnEntityService.saveColumn(typeList);
        Mono.fromCallable(()->{

        });
        NoteEntity noteEntity = noteEntityRepository.save(new NoteEntity(columnList));
        columnEntityService.updateColumnWithNoteId(columnList, noteEntity.id);
        return noteEntity.id;
    }

    
    public boolean saveColumn(ColumnEntity column){
        try{
            columnRepository.save(column);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    
    public NoteEntity getNoteById(String id){
        return noteEntityRepository.findById(id).orElseThrow();
    }

    
    public void deleteNote(String noteEntityId){
        noteEntityRepository.deleteById(noteEntityId);
    }

    
    public NoteEntity updateNote(NoteEntity noteEntity){
        return noteEntityRepository.save(noteEntity);
    }

    
    public NoteEntity removeColumnFromNote(String noteEntityId, String columnEntityId){
        NoteEntity noteEntity = noteEntityRepository.findById(noteEntityId).orElseThrow();
        noteEntity.columnsId.remove(columnEntityService.findColumnEntityById(columnEntityId).id);
        columnEntityService.deleteById(noteEntityId, columnEntityId);
        return updateNote(noteEntity);
    }
}
