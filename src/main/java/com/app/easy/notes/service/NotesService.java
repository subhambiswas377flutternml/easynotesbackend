package com.app.easy.notes.service;

import com.app.easy.notes.DTO.request.NotesDto;
import com.app.easy.notes.entity.NotesEntity;
import com.app.easy.notes.entity.UserEntity;
import com.app.easy.notes.repository.NotesRepository;
import com.app.easy.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NotesService {
    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private UserRepository userRepository;

    private UserEntity checkUserExists(int userId){
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new NoSuchElementException("User doesn't exist !");
        }else{
            return user.get();
        }
    }

    private NotesEntity getNoteFromNoteId(int noteId){
        Optional<NotesEntity> note = notesRepository.findById(noteId);
        if(note.isEmpty()){
            throw new NoSuchElementException("Note doesn't exist !");
        }else{
            return note.get();
        }
    }

    public List<NotesEntity> getNotesByUserId(int userId, Boolean checkUser){
        if(checkUser){
            checkUserExists(userId);
        }

        Optional<List<NotesEntity>> notes = notesRepository.findByUserId(userId);
        if(notes.isPresent()){
            return notes.get();
        }else{
            return List.of();
        }
    }

    public List<NotesEntity> upsertNote(NotesDto notesDto, Boolean isUpdate){
        checkUserExists(notesDto.getUserId());

        NotesEntity existingNote = null;
        NotesEntity newNote = null;

        if(isUpdate){
            existingNote = getNoteFromNoteId(notesDto.getNoteId());
            existingNote.setTitle(notesDto.getTitle());
            existingNote.setDescription(notesDto.getDescription());
        }else{
            newNote = new NotesEntity();
            newNote.setUserId(notesDto.getUserId());
            newNote.setDescription(notesDto.getDescription());
            newNote.setTitle(notesDto.getTitle());
        }

        if(isUpdate){
            notesRepository.save(existingNote);
        }else{
            notesRepository.save(newNote);
        }

        return getNotesByUserId(notesDto.getUserId(), false);
    }

    public List<NotesEntity> deleteNote(int noteId){
        NotesEntity note = getNoteFromNoteId(noteId);
        notesRepository.deleteById(noteId);
        return getNotesByUserId(note.getUserId(),false);
    }
}
