package com.harshit.notes.services;

import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(UsersEntity user){
        userRepository.save(user);
    }

    public List<UsersEntity> getAll(){
        return userRepository.findAll();
    }

    public UsersEntity getById(ObjectId id){
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteById(ObjectId id){
        userRepository.deleteById(id);
        return true;
    }

    public UsersEntity findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
