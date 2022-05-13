package org.nc.core.controller;

import org.nc.core.config.security.roles.AdminRole;
import org.nc.core.config.security.roles.DeveloperRole;
import org.nc.core.config.security.roles.RoleEnum;
import org.nc.core.entity.UserEntity;
import org.nc.core.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public Mono<UserEntity> getUserByUsername(@RequestParam String username) {
        return userService.getUser(username);
    }

    @PostMapping("addRole")
    @ResponseBody
    @DeveloperRole
    @AdminRole
    public Mono<UserEntity> addRole(@RequestParam String userId, @RequestParam RoleEnum roleEnum){
        return userService.addUserRole(userId, roleEnum);
    }
}
