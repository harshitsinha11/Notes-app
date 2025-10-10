package com.harshit.notes.controller;


import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.repository.UserRepository;
import com.harshit.notes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/get-all-users")
    public List<UsersEntity> getAllUser(){
        return userService.getAll();
    }

    @PostMapping("/create-new-admin")
    public void createUser(@RequestBody UsersEntity user){
        userService.saveNewAdminUser(user);
    }

}
