package org.nc.core.repository;

import org.nc.core.entity.UserGroupEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends MongoRepository<UserGroupEntity, String> {
}
