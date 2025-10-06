package com.harshit.notes.services;

import com.harshit.notes.entity.NotesEntity;
import com.harshit.notes.repository.NotesRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    public void saveEntry(NotesEntity notesEntity){
        notesEntity.setDate(LocalDateTime.now());
        notesRepository.save(notesEntity);
    }

    public List<NotesEntity> getAll(){
        return notesRepository.findAll();
    }

    public NotesEntity getById(ObjectId id){
        return notesRepository.findById(id).orElse(null);
    }

    public boolean deleteById(ObjectId id){
        notesRepository.deleteById(id);
        return true;
    }

    public void deleteAll(){
        notesRepository.deleteAll();
    }
}
