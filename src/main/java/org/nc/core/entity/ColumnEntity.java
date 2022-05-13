package org.nc.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Document("columns")
public class ColumnEntity<T> {
    @Id
    public String id;

    public TypeEnum type;
    public Integer order;
    public String noteId;
    public LinkedList<T> data;

    public ColumnEntity(TypeEnum type) {
        this.type = type;
        this.data = new LinkedList<>();
    }
}
