package org.nc.core.service;

import org.nc.core.config.security.roles.RoleEnum;
import org.nc.core.entity.UserEntity;
import org.nc.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    public Mono<UserEntity> getUser(String username){
        return userRepository.findByUsername(username);
    }

    public Mono<UserEntity> addUserRole(String userId, RoleEnum roleEnum){
        Mono<UserEntity> userEntity = userRepository.findById(userId);
        return userEntity.flatMap(user -> {
            user.addRole(roleEnum);
            return userRepository.save(user);
        });
    }
}
