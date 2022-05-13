package org.nc.core.controller;

import org.nc.core.entity.ColumnEntity;
import org.nc.core.service.ColumnEntityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("column")
public class ColumnEntityController {
    private final ColumnEntityService columnEntityService;

    public ColumnEntityController(ColumnEntityService columnEntityService) {
        this.columnEntityService = columnEntityService;
    }

    @PostMapping
    @ResponseBody
    public String addColumnData(@RequestBody Object data, @RequestParam String columnId){
       return columnEntityService.addData(columnId, data);
    }

    @PutMapping
    @ResponseBody
    public String updateData(@RequestParam Integer index, @RequestBody Object newData, @RequestParam String columnId){
        return columnEntityService.updateData(index, newData, columnId);
    }

    @GetMapping
    @ResponseBody
    public List<ColumnEntity> getAllColumns(@RequestParam String noteId){
        return columnEntityService.getAllColumnsByNoteId(noteId);
    }
}
