package org.nc.core.repository;

import org.nc.core.entity.NoteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteEntityRepository extends ReactiveMongoRepository<NoteEntity, String> {
}
