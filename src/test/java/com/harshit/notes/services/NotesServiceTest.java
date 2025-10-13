package com.harshit.notes.services;

import com.harshit.notes.entity.NotesEntity;
import com.harshit.notes.entity.UsersEntity;
import com.harshit.notes.repository.NotesRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotesServiceTest {

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private NotesService notesService;

    private UsersEntity user;
    private NotesEntity note;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = UsersEntity.builder()
                .id(new ObjectId())
                .userName("NewUser")
                .password("1234")
                .build();

        note = new NotesEntity();
        note.setId(new ObjectId());
        note.setTitle("Note 1");

        user.setNotesEntities(new ArrayList<>());
        user.getNotesEntities().add(note);
    }

    @Test
    void testFindNotesEntityOrNull_Found() {
        when(userService.findByUserName("NewUser")).thenReturn(user);
        NotesEntity result = notesService.findNotesEntityOrNull("NewUser", note.getId());
        assertNotNull(result);
        assertEquals("Note 1", result.getTitle());
    }

    @Test
    void testFindNotesEntityOrNull_NotFound() {
        when(userService.findByUserName("NewUser")).thenReturn(user);
        NotesEntity result = notesService.findNotesEntityOrNull("NewUser", new ObjectId());
        assertNull(result);
    }

    @Test
    void testSaveEntry_WithUser() {
        when(userService.findByUserName("NewUser")).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(true);
        when(notesRepository.save(note)).thenReturn(note);

        notesService.saveEntry(note, "NewUser");

        assertTrue(user.getNotesEntities().contains(note));
        verify(notesRepository).save(note);
        verify(userService).saveUser(user);
    }

    @Test
    void testSaveEntry_WithoutUser() {
        when(notesRepository.save(note)).thenReturn(note);

        notesService.saveEntry(note);

        verify(notesRepository).save(note);
        verifyNoInteractions(userService);
    }

    @Test
    void testGetAll() {
        when(notesRepository.findAll()).thenReturn(List.of(note));
        List<NotesEntity> result = notesService.getAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetById_Found() {
        when(notesRepository.findById(note.getId())).thenReturn(Optional.of(note));
        NotesEntity result = notesService.getById(note.getId());
        assertNotNull(result);
        assertEquals("Note 1", result.getTitle());
    }

    @Test
    void testGetById_NotFound() {
        when(notesRepository.findById(new ObjectId())).thenReturn(Optional.empty());
        NotesEntity result = notesService.getById(new ObjectId());
        assertNull(result);
    }

    @Test
    void testDeleteById_Success() {
        when(userService.findByUserName("NewUser")).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(true);

        boolean result = notesService.deleteById(note.getId(), "NewUser");

        assertTrue(result);
        verify(notesRepository).deleteById(note.getId());
        verify(userService).saveUser(user);
    }

    @Test
    void testDeleteById_Failure() {
        when(userService.findByUserName("NewUser")).thenReturn(user);

        boolean result = notesService.deleteById(new ObjectId(), "NewUser");

        assertFalse(result);
        verify(notesRepository, never()).deleteById(any());
        verify(userService, never()).saveUser(any());
    }

}
