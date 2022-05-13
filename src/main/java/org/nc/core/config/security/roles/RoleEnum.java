package org.nc.core.config.security.roles;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ALL,
    USER,
    DEVELOPER,
    ADMIN,
    EDITOR;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
