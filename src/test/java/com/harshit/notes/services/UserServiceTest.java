package com.harshit.notes.services;

import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UsersEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = UsersEntity.builder()
                .id(new ObjectId())
                .userName("NewUser")
                .password("1234")
                .build();
    }

    @Test
    public void testSaveUser_Success() {
        when(userRepository.save(user)).thenReturn(user);
        boolean isSaved = userService.saveUser(user);
        assertTrue(isSaved);
    }

    @Test
    public void testSaveUser_Failure() {
        when(userRepository.save(user)).thenThrow(new RuntimeException("DB Error"));
        boolean isSaved = userService.saveUser(user);
        assertFalse(isSaved);
    }

    @Test
    public void testSaveNewUser_Failure() {
        when(userRepository.save(user)).thenThrow(new RuntimeException("DB Error"));
        boolean isSaved = userService.saveNewUser(user);
        assertFalse(isSaved);
    }

    @Test
    public void testSaveNewUser_RoleCount() {
        when(userRepository.save(user)).thenReturn(user);
        boolean result = userService.saveNewUser(user);
        assertTrue(result);
        assertEquals(1, user.getRoles().size());
    }

    @Test
    public void testSaveNewAdminUser_Failure() {
        when(userRepository.save(user)).thenThrow(new RuntimeException("DB Error"));
        boolean isSaved = userService.saveNewAdminUser(user);
        assertFalse(isSaved);
    }

    @Test
    public void testSaveNewAdminUser_RoleCount() {
        when(userRepository.save(user)).thenReturn(user);
        boolean result = userService.saveNewAdminUser(user);
        assertTrue(result);
        assertEquals(2, user.getRoles().size());
    }

    @Test
    void testGetAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UsersEntity> listOfUsers = userService.getAll();
        assertNotNull(listOfUsers);
    }

    @Test
    void testGetById(){
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        UsersEntity result = userService.getById(user.getId());
        assertNotNull(result);
    }

    @Test
    void testGetById_Found() {
        ObjectId id = new ObjectId();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UsersEntity result = userService.getById(id);
        assertNotNull(result);
        assertEquals("NewUser", result.getUserName());
    }

    @Test
    void testGetById_NotFound() {
        ObjectId id = new ObjectId();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UsersEntity result = userService.getById(id);
        assertNull(result);
    }

    @Test
    void testFindByUserName() {
        when(userRepository.findByUserName("NewUser")).thenReturn(user);
        UsersEntity result = userService.findByUserName("NewUser");
        assertEquals(user, result);
    }

}
