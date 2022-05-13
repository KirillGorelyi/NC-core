package org.nc.core.service;

import org.nc.core.config.security.roles.RoleEnum;
import org.nc.core.entity.UserEntity;
import org.nc.core.exception.UserAlreadyExistException;
import org.nc.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    public UserEntity getUser(String username){
        return userRepository.findByUsername(username);
    }

    public UserEntity addUserRole(String userId, RoleEnum roleEnum){
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        userEntity.addRole(roleEnum);
        userRepository.save(userEntity);
        return userEntity;
    }
}
