package com.harshit.notes.controler;


import com.harshit.notes.entity.NotesEntity;
import com.harshit.notes.repository.NotesRepository;
import com.harshit.notes.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestRepo {

    @Autowired
    NotesRepository repo;

    @GetMapping
    public List<NotesEntity> test(){
        NotesEntity n = new NotesEntity("Title");
        n.setContent("Content");
        repo.save(n);
        return repo.findAll();
    }


}
