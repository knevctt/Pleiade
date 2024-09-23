package app.Pleiade.Service;

import app.Pleiade.Entity.User;
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

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
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
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.findAll();
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
    }

    @Test
    void findUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.findById(1L);
        assertEquals("John", result.getName());
    }

    @Test
    void findUserByName() {
        User user = new User();
        user.setName("John");

        when(userRepository.findByName("John")).thenReturn(Optional.of(user));
        Optional<User> result = userService.findByNome("John");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }
}
