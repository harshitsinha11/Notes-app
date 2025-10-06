package com.harshit.notes.services;

import com.harshit.notes.entity.NotesEntity;
import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.repository.NotesRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(NotesEntity notesEntity, String userName){
        UsersEntity user = userService.findByUserName(userName);
        notesEntity.setDate(LocalDateTime.now());
        user.getNotesEntities().add(notesEntity);
        notesRepository.save(notesEntity);
        userService.saveEntry(user);
    }

    public void saveEntry(NotesEntity notesEntity){
        notesRepository.save(notesEntity);
    }

    public List<NotesEntity> getAll(){
        return notesRepository.findAll();
    }

    public NotesEntity getById(ObjectId id){
        return notesRepository.findById(id).orElse(null);
    }

    public boolean deleteById(ObjectId id, String userName){
        UsersEntity user = userService.findByUserName(userName);
        user.getNotesEntities().removeIf(t -> t.getId().equals(id));
        userService.saveEntry(user);
        notesRepository.deleteById(id);
        return true;
    }

    public void deleteAll(){
        notesRepository.deleteAll();
    }
}
