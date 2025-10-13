package com.harshit.notes.controller;


import com.harshit.notes.entity.NotesEntity;
import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.services.NotesService;
import com.harshit.notes.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesEntryController {

    @Autowired
    private NotesService notesService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllNotesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UsersEntity user = userService.findByUserName(userName);
        List<NotesEntity> allUserNotes = user.getNotesEntities();
        if (allUserNotes != null) {
            return new ResponseEntity<>(allUserNotes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<NotesEntity> getById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        NotesEntity notesEntity = notesService.findNotesEntityOrNull(userName,id);
        if (notesEntity != null) {
            return new ResponseEntity<>(notesEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<NotesEntity> createEntry(@RequestBody NotesEntity notesEntity) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            notesService.saveEntry(notesEntity, userName);
            return new ResponseEntity<>(notesEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = notesService.deleteById(id, userName);
        if(removed)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<NotesEntity> updateById(@PathVariable ObjectId id, @RequestBody NotesEntity newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        NotesEntity oldEntry = notesService.findNotesEntityOrNull(userName,id);
        if (oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null &&!newEntry.getTitle().trim().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().trim().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            notesService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
