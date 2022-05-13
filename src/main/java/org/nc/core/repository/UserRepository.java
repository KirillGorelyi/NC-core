package org.nc.core.repository;

import org.nc.core.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    boolean existsByEmail(String eMail);
    UserEntity findByUsernameAndPassword(String userName, String password);
    UserEntity findByUsername(String username);
}
