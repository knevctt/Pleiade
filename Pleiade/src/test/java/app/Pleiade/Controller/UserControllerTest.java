package app.Pleiade.Controller;

import app.Pleiade.Entity.User;
import app.Pleiade.Repository.UserRepository;
import app.Pleiade.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController userController;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserService userService;

    @BeforeEach
    void setup(){
        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setAccessLevel(0);
        user1.setName("John ");
        user1.setLastName("Doe");
        user1.setUserName("jhon@doe");
        user1.setPassword("12345");
        user1.setEmail("john.doe@example.com");

        users.add(user1);

        User user2 = new User();
        user2.setAccessLevel(0);
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setLastName("Doe");
        user2.setUserName("jane@doe");
        user2.setPassword("12345");

        users.add(user2);

        User user3 = new User();
        user3.setAccessLevel(0);
        user3.setName("Michael Doe");
        user3.setEmail("michael.doe@example.com");
        user3.setLastName("Doe");
        user3.setUserName("michael@doe");
        user3.setPassword("12345");

        users.add(user3);

        when(userService.findAll()).thenReturn(users);
        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.findById(3L)).thenReturn(Optional.of(user3));
        when(userService.save(any(User.class))).thenReturn("User saved successfully");
    }

    @Test
    void testFindAll() {
        ResponseEntity<List<User>> retorno = this.userController.findAll();
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(3, retorno.getBody().size());
        //testa se no findAll a quantidade de usuarios é 3 como deveria ser no setup
    }

    @Test
    void testFindAll2() {
        ResponseEntity<List<User>> retorno = this.userController.findAll();
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        //testa se o findAll está retornando status http 200
    }


    void testFindById() {
        ResponseEntity<User> retorno = this.userController.findById(1L);
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        //testa se o findById encontra usuario com id 1 e Long
    }

    @Test
    void testFindByIdWithException() {
        // Simula uma exceção sendo lançada pelo service
        when(userService.findById(1L)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<User> retorno = this.userController.findById(1L);
        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals(null, retorno.getBody());
    }

    @Test
    void testFindAllWithException() {
        when(userService.findAll()).thenThrow(new RuntimeException("Database error"));
        ResponseEntity<List<User>> retorno = this.userController.findAll();
        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals(null, retorno.getBody());
    }

    @Test
    void testSave() {
        User newUser = new User();
        newUser.setName("New User");

        ResponseEntity<String> response = userController.save(newUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User saved successfully", response.getBody());
    }

    @Test
    void testSaveException() {
        when(userService.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        User newUser = new User();
        newUser.setName("New User");

        ResponseEntity<String> response = userController.save(newUser);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

}
