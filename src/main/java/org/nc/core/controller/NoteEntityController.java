package org.nc.core.controller;

import org.nc.core.service.NoteEntityService;
import org.nc.core.entity.NoteEntity;
import org.nc.core.entity.TypeEnum;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("note")
public class NoteEntityController {

    private final NoteEntityService noteEntityService;

    public NoteEntityController(NoteEntityService noteEntityService) {
        this.noteEntityService = noteEntityService;
    }

    @PostMapping
    @ResponseBody
    public String createNote(@RequestBody List<TypeEnum> typeList){
        return noteEntityService.saveNote(typeList);
    }

    @GetMapping
    @ResponseBody
    public NoteEntity getEntity(@RequestParam String noteId){
        return noteEntityService.getNoteById(noteId);
    }

    @PutMapping
    @ResponseBody
    public String updateNote(@RequestBody NoteEntity noteEntity){
        return noteEntityService.updateNote(noteEntity).id;
    }

    @DeleteMapping
    @ResponseBody
    public void deleteNote(@RequestParam String entityId){
        noteEntityService.deleteNote(entityId);
    }

    @DeleteMapping("/dropColumn")
    @ResponseBody
    public void removeColumn(@RequestParam String noteEntityId, @RequestParam String columnId){
        noteEntityService.removeColumnFromNote(noteEntityId, columnId);
    }
}