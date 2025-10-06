package com.harshit.notes.controler;


import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UsersEntity> getAllUser(){
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody UsersEntity user){
        userService.saveEntry(user);
    }

    @PutMapping("/{userName}")
    public void updateUser(@RequestBody UsersEntity newUser,@PathVariable String userName){
        UsersEntity oldUser = userService.findByUserName(userName);
        if(oldUser != null){
            oldUser.setUserName(newUser.getUserName());
            oldUser.setPassword(newUser.getPassword());
            userService.saveEntry(oldUser);
        }
    }
}
