package org.nc.core.entity;

import org.nc.core.config.security.roles.AdminRole;
import org.nc.core.config.security.roles.DeveloperRole;
import org.nc.core.config.security.roles.EditorRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("user_group")
public class UserGroupEntity {
    @Id
    public String id;

    private String masterId;
    private final List<String> slavesId = new ArrayList<>();

    public UserGroupEntity(String masterId) {
        this.masterId = masterId;
    }

    @EditorRole
    @DeveloperRole
    @AdminRole
    public void addSlave(UserEntity entity, String slaveId) {
        slavesId.add(slaveId);
    }

    public List<String> getSlavesId() {
        return new ArrayList<>(this.slavesId);
    }

    public void changeMaster(String masterId){
        this.masterId = masterId;
    }
}

