package org.nc.core.service;

import org.nc.core.entity.UserGroupEntity;
import org.nc.core.repository.UserGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserGroupService {
    private final UserGroupRepository userGroupRepository;

    public UserGroupService(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    public Mono<UserGroupEntity> createUserGroup(UserGroupEntity userGroup){
        return userGroupRepository.save(userGroup);
    }

    public Mono<UserGroupEntity> updateUserGroup(UserGroupEntity userGroup){
        return userGroupRepository.save(userGroup);
    }

    public Mono<UserGroupEntity> getUserGroupById(String id){
        return userGroupRepository.findById(id);
    }

    public Mono<Void> deleteUserGroup(String id){
        return userGroupRepository.deleteById(id);
    }
}
