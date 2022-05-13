package org.nc.core.controller;

import io.netty.channel.unix.Errors;
import org.nc.core.config.security.roles.AdminRole;
import org.nc.core.config.security.roles.DeveloperRole;
import org.nc.core.config.security.roles.RoleEnum;
import org.nc.core.entity.UserEntity;
import org.nc.core.exception.UserAlreadyExistException;
import org.nc.core.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import reactor.netty.http.server.HttpServerRequest;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public UserEntity getUserByUsername(@RequestParam String username) {
        return userService.getUser(username);
    }

    @PostMapping("addRole")
    @ResponseBody
    @DeveloperRole
    @AdminRole
    public UserEntity addRole(@RequestParam String userId, @RequestParam RoleEnum roleEnum){
        return userService.addUserRole(userId, roleEnum);
    }
}
