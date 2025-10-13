package com.harshit.notes.services;

import com.harshit.notes.entity.NotesEntity;
import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.repository.NotesRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private UserService userService;

    public NotesEntity findNotesEntityOrNull(String userName, ObjectId noteId) {
        UsersEntity user = userService.findByUserName(userName);
        return user.getNotesEntities().stream()
                .filter(note -> note.getId().equals(noteId))
                .findAny()
                .orElse(null);
    }

    @Transactional
    public void saveEntry(NotesEntity notesEntity, String userName){
        try{
            log.info("Saving entry for user: {}",userName);
            UsersEntity user = userService.findByUserName(userName);
            notesEntity.setDate(LocalDateTime.now());
            user.getNotesEntities().add(notesEntity);
            notesRepository.save(notesEntity);
            userService.saveUser(user);
        } catch (Exception e) {
            log.error("Some error occurred in saving user notes : {} \n {}",notesEntity.getContent(),userName);
            throw new RuntimeException("An error occurred while saving the entry",e);
        }
    }

    public NotesEntity getById(ObjectId id){
        return notesRepository.findById(id).orElse(null);
    }


    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        try{
            UsersEntity user = userService.findByUserName(userName);
            boolean removed = user.getNotesEntities().removeIf(t -> t.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                notesRepository.deleteById(id);
                log.info("Given entry: {} deleted for user: {}",id,userName);
            } else {
                log.warn("Failed to find the entry: {} by the user: {}",id,userName);
            }
            return removed;
        } catch (Exception e){
            log.error("Some error occurred in deleting entry: {} for the user: {}",id,userName);
            return false;
        }
    }

}
