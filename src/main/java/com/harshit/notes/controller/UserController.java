package com.harshit.notes.controller;


import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UsersEntity newUser){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            UsersEntity oldUser = userService.findByUserName(userName);
            if(oldUser != null){
                oldUser.setUserName(newUser.getUserName());
                oldUser.setPassword(newUser.getPassword());
                userService.saveUser(oldUser);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSelfUser(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            UsersEntity user = userService.findByUserName(userName);
            userService.deleteByUserName(userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            System.out.println("Error occurred in Deleting User" + e);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
