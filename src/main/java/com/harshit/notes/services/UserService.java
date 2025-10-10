package com.harshit.notes.services;

import com.harshit.notes.entity.NotesEntity;
import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(UsersEntity user) {
        userRepository.save(user);
    }

    public void saveNewUser(UsersEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public void saveNewAdminUser(UsersEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public List<UsersEntity> getAll() {
        return userRepository.findAll();
    }

    public UsersEntity getById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public UsersEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public NotesEntity findNotesEntityOrNull(String userName, ObjectId noteId) {
        UsersEntity user = userRepository.findByUserName(userName);
        return user.getNotesEntities().stream()
                .filter(note -> note.getId().equals(noteId))
                .findAny()
                .orElse(null);
    }
}
