package org.nc.core.repository;

import org.nc.core.entity.ColumnEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnRepository extends MongoRepository<ColumnEntity, String> {
    List<ColumnEntity> findAllByNoteId(String noteId);
}
