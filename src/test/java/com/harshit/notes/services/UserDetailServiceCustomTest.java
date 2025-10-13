package com.harshit.notes.services;

import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailServiceCustomTest {
    @InjectMocks
    private UserDetailServiceCustom userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(UsersEntity.builder().userName("ram").password("inrinrick").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
    @Test
    void loadUserByUsernameTest_Fail(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(null);
        UserDetails user = userDetailsService.loadUserByUsername("ram");
        Assertions.assertNull(user);
    }
}
