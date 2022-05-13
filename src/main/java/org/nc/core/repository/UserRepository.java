package org.nc.core.repository;

import org.nc.core.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {
    Mono<Boolean> existsByEmail(String eMail);
    Mono<UserEntity> findByUsernameAndPassword(String userName, String password);
    Mono<UserEntity> findByUsername(String username);
}
