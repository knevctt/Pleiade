package app.Pleiade.Service;

import app.Pleiade.Entity.UserEntity;
import app.Pleiade.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserEntityServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@example.com");
    }

    @Test
    void saveUser() {
        when(userRepository.save(user)).thenReturn(user);
        String message = userService.save(user);
        assertEquals("User saved sucessfully", message);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser() {
        when(userRepository.save(user)).thenReturn(user);
        String message = userService.update(user, 1L);
        assertEquals("User updated sucessfully", message);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        String message = userService.delete(1L);
        assertEquals("User deleted sucessfully", message);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAllUsers() {
        List<UserEntity> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);
        List<UserEntity> result = userService.findAll();
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
    }

    @Test
    void findUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserEntity result = userService.findById(1L);
        assertEquals("John", result.getName());
    }

    @Test
    void findUserByName() {
        UserEntity user = new UserEntity();
        user.setName("John");

        when(userRepository.findByName("John")).thenReturn(Optional.of(user));
        Optional<UserEntity> result = userService.findByName("John");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }
}

