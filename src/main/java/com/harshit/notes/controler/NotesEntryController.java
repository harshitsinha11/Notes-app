package com.harshit.notes.controler;


import com.harshit.notes.entity.NotesEntity;
import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.services.NotesService;
import com.harshit.notes.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesEntryController {

    @Autowired
    private NotesService notesService;

    @Autowired
    private UserService userService;

    @PostMapping("/{userName}")
    public ResponseEntity<NotesEntity> createEntry(@RequestBody NotesEntity notesEntity, @PathVariable String userName){
        try {
            notesService.saveEntry(notesEntity,userName);
            return new ResponseEntity<>(notesEntity , HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllNotesByUser(@PathVariable String userName){

        UsersEntity user = userService.findByUserName(userName);
        List<NotesEntity> list = user.getNotesEntities();
        if (list != null){ return new ResponseEntity<>(list,HttpStatus.OK); }
        else { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<NotesEntity> getById(@PathVariable ObjectId id){
        NotesEntity notesEntity = notesService.getById(id);
        if(notesEntity != null){
            return new ResponseEntity<>(notesEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userName}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id,@PathVariable String userName){
        return new ResponseEntity<>(notesService.deleteById(id,userName), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/id")
    public String deleteAll(){
        notesService.deleteAll();
        return ":D";
    }

    @PutMapping("/id/{userName}/{id}")
    public ResponseEntity<NotesEntity> updateById(@PathVariable ObjectId id,@RequestBody NotesEntity newEntry){
        NotesEntity oldEntry = notesService.getById(id);
        if(oldEntry != null){
            oldEntry.setTitle(!newEntry.getTitle().trim().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().trim().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            notesService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
