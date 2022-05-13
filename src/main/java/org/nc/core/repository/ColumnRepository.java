package org.nc.core.repository;

import org.nc.core.entity.ColumnEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ColumnRepository extends ReactiveMongoRepository<ColumnEntity, String> {
    Mono<List<ColumnEntity>> findAllByNoteId(String noteId);
}
