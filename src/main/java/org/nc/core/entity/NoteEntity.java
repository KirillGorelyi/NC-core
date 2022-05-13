package org.nc.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Document("main_note")
public class NoteEntity {
    @Id
    public String id;

    public String userId;

    public LinkedList<String> columnsId;

    public NoteEntity(LinkedList<String> columnsId){
        this.columnsId = columnsId;
    }
}
