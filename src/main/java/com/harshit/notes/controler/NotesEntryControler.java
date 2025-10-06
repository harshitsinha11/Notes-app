package com.harshit.notes.controler;


import com.harshit.notes.entity.NotesEntity;
import com.harshit.notes.services.NotesService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NotesEntryControler {

    @Autowired
    private NotesService notesService;

    @PostMapping
    public NotesEntity createEntry(@RequestBody NotesEntity notesEntity){
        notesService.saveEntry(notesEntity);
        return notesEntity;
    }

    @GetMapping
    public List<NotesEntity> getAll(){
        return notesService.getAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<NotesEntity> getById(@PathVariable ObjectId id){
        NotesEntity notesEntity = notesService.getById(id);
        if(notesEntity != null){
            return new ResponseEntity<>(notesEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteById(@PathVariable ObjectId id){
        return notesService.deleteById(id);
    }

    @DeleteMapping("/id")
    public String deleteAll(){
        notesService.deleteAll();
        return ":D";
    }

    @PutMapping("/id/{id}")
    public NotesEntity updateById(@PathVariable ObjectId id,@RequestBody NotesEntity newEntry){
        NotesEntity oldEntry = notesService.getById(id);
        if(oldEntry != null){
            oldEntry.setTitle(!newEntry.getTitle().trim().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().trim().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
        }
        notesService.saveEntry(oldEntry);
        return oldEntry;
    }

}
