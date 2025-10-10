package com.harshit.notes.controller;

import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String health(){
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createNewUser(@RequestBody UsersEntity user){
        userService.saveNewUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
