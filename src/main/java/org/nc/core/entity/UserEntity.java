package org.nc.core.entity;

import org.nc.core.config.security.roles.AdminRole;
import org.nc.core.config.security.roles.DeveloperRole;
import org.nc.core.config.security.roles.EditorRole;
import org.nc.core.config.security.roles.RoleEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.NoSuchAlgorithmException;
import java.util.*;

@Document("user")
public class UserEntity implements UserDetails {

    @Id
    public String id;

    public String name;
    public String surname;
    public String eMail;
    private String password;
    public String username;
    public boolean isLockedToMaster;
    public List<UserGroupEntity> roleUserGroups = new ArrayList<>();
    private final Set<RoleEnum> roles = new HashSet<>();

    public UserEntity(String id, String name, String surname, String eMail,String username, String password) throws NoSuchAlgorithmException {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.eMail = eMail;
        this.password = password;
        this.username = username;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getPassword(){
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setEmail(String eMail) {
        this.eMail = eMail;
    }

    public void setPassword(String password) { this.password = password;}

    @AdminRole
    @DeveloperRole
    public void addRole(RoleEnum roleEnum){
        roles.add(roleEnum);
    }

    public boolean roleContains(RoleEnum roleEnum){
        return roles.contains(roleEnum);
    }

    @EditorRole
    @DeveloperRole
    @AdminRole
    private void addRoleUserGroup(UserGroupEntity roleUserGroup){
        this.roleUserGroups.add(roleUserGroup);
    }
}
