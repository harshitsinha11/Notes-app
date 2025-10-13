package com.harshit.notes.services;

import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveUser(UsersEntity user) {
        try {
            userRepository.save(user);
            log.info("Updated user: {}", user.getUserName());
            return true;
        } catch (Exception e) {
            log.error("Failed to save update user : {}", user.getUserName(), e);
            return false;
        }
    }

    public boolean saveNewUser(UsersEntity user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            log.info("Created new user: {}", user.getUserName());
            return true;
        } catch (Exception e) {
            log.error("Failed  to create new user: {}", user.getUserName(), e);
            return false;
        }
    }

    public boolean saveNewAdminUser(UsersEntity user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER", "ADMIN"));
            userRepository.save(user);
            log.info("Created new admin user: {}", user.getUserName());
            return true;
        } catch (Exception e) {
            log.error("Failed to create new Admin User: {}", user.getUserName(), e);
            return false;
        }
    }

    public List<UsersEntity> getAll() {
        log.info("Fetching Details of all users");
        return userRepository.findAll();
    }

    public UsersEntity getById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteByUserName(String userName) {
        try {
            log.info("Deleted User: {}", userName);
            userRepository.deleteByUserName(userName);
            return true;
        } catch (Exception e) {
            log.error("Failed to delete User: {}", userName);
            return false;
        }
    }

    public UsersEntity findByUserName(String userName) {
        try {
            return userRepository.findByUserName(userName);
        } catch (Exception e) {
            log.error("Username not found; {}", userName);
            return null;
        }
    }

}
