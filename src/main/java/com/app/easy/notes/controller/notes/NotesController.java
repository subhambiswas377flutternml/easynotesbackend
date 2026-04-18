package com.app.easy.notes.controller.notes;

import com.app.easy.notes.DTO.request.NotesDto;
import com.app.easy.notes.entity.NotesEntity;
import com.app.easy.notes.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public List<NotesEntity> getNotes(@PathVariable(value = "userId") int userId){
        return notesService.getNotesByUserId(userId, true);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public List<NotesEntity> createNote(@RequestBody NotesDto notesDto){
        return notesService.upsertNote(notesDto, false);
    }

    @PostMapping(value = "/update")
    public List<NotesEntity> updateNote(@RequestBody NotesDto notesDto){
        return notesService.upsertNote(notesDto, true);
    }

    @DeleteMapping(value = "/{noteId}")
    public List<NotesEntity> deleteNote(@PathVariable(value = "noteId") int noteId){
        return notesService.deleteNote(noteId);
    }
}
