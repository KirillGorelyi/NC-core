package org.nc.core.controller;

import org.nc.core.entity.UserGroupEntity;
import org.nc.core.service.UserGroupService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("userGroup")
public class UserGroupController {
    private final UserGroupService userGroupService;

    public UserGroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    @GetMapping
    @ResponseBody
    public UserGroupEntity getById(@RequestParam String id){
        return userGroupService.getUserGroupById(id);
    }

    @PostMapping
    @ResponseBody
    public UserGroupEntity save(@RequestBody UserGroupEntity userGroup){
        return userGroupService.createUserGroup(userGroup);
    }

    @PutMapping
    @ResponseBody
    public Mono<UserGroupEntity> update(UserGroupEntity userGroup){
        return Mono.fromCallable(() -> userGroupService.updateUserGroup(userGroup));
    }

    @DeleteMapping
    @ResponseBody
    public Mono<ServerResponse> delete(String id){
        userGroupService.deleteUserGroup(id);
        return ServerResponse.ok().bodyValue("ok");
    }
}
