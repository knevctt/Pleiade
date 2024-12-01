package app.Pleiade.Controller;

import app.Pleiade.Entity.UserEntity;
import app.Pleiade.Repository.UserRepository;
import app.Pleiade.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

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
        List<UserEntity> users = new ArrayList<>();

        UserEntity user1 = new UserEntity();
        user1.setName("John ");
        user1.setLastName("Doe");
        user1.setUserName("jhon@doe");
        user1.setPassword("12345");
        user1.setEmail("john.doe@example.com");

        users.add(user1);

        UserEntity user2 = new UserEntity();
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setLastName("Doe");
        user2.setUserName("jane@doe");
        user2.setPassword("12345");

        users.add(user2);

        UserEntity user3 = new UserEntity();
        user3.setName("Michael Doe");
        user3.setEmail("michael.doe@example.com");
        user3.setLastName("Doe");
        user3.setUserName("michael@doe");
        user3.setPassword("12345");

        users.add(user3);

        when(userService.findAll()).thenReturn(users);
        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.findById(3L)).thenReturn(Optional.of(user3));
        when(userService.delete(3L)).thenReturn("User deleted successfully");
        when(userService.save(any(UserEntity.class))).thenReturn("User saved successfully");
        when(userService.update(any(UserEntity.class), Mockito.eq(3L))).thenReturn("User updated successfully");
        when(userService.findByName("Michael Doe")).thenReturn(Optional.of(user3));
    }

    @Test
    void testFindAll() {
        ResponseEntity<List<UserEntity>> retorno = this.userController.findAll();
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(3, retorno.getBody().size());
        //testa se no findAll a quantidade de usuarios é 3 como deveria ser no setup
    }

    @Test
    void testFindAll2() {
        ResponseEntity<List<UserEntity>> retorno = this.userController.findAll();
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        //testa se o findAll está retornando status http 200
    }

    @Test
    void testFindById() {
        ResponseEntity<UserEntity> retorno = this.userController.findById(1L);
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        //testa se o findById encontra usuario com id 1 e Long
    }

    @Test
    void testFindByIdWithException() {
        // Simula uma exceção sendo lançada pelo service
        when(userService.findById(1L)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<UserEntity> retorno = this.userController.findById(1L);
        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals(null, retorno.getBody());
    }

    @Test
    void testFindAllWithException() {
        when(userService.findAll()).thenThrow(new RuntimeException("Database error"));
        ResponseEntity<List<UserEntity>> retorno = this.userController.findAll();
        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals(null, retorno.getBody());
    }

    @Test
    void testSave() {
        UserEntity newUser = new UserEntity();
        newUser.setName("New User");

        ResponseEntity<String> response = userController.save(newUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User saved successfully", response.getBody());
    }

    @Test
    void testSaveException() {
        when(userService.save(any(UserEntity.class))).thenThrow(new RuntimeException("Database error"));

        UserEntity newUser = new UserEntity();
        newUser.setName("New User");

        ResponseEntity<String> response = userController.save(newUser);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testDelete() {
        ResponseEntity<String> response = userController.delete(3L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }

    @Test
    void testDeleteException() {
        when(userService.delete(3L)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<String> response = userController.delete(3L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testUpdate() {
        UserEntity updatedUser = new UserEntity();
        updatedUser.setName("Updated Name");

        ResponseEntity<String> response = userController.update(updatedUser, 3L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", response.getBody());
    }

//    @Test
//    void testUpdateException() {
//        when(userService.update(any(UserEntity.class), Mockito.eq(3L))).thenThrow(new RuntimeException("Database error"));
//
//        UserEntity updatedUser = new (UserEntity);
//        updatedUser.setName("Updated Name");
//
//        ResponseEntity<String> response = userController.update(updatedUser, 3L);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals(null, response.getBody());
//    }

    @Test
    void testFindByName() {
        ResponseEntity<UserEntity> response = userController.findByName("Michael Doe");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Michael Doe", response.getBody().getName());
    }

    @Test
    void testFindByNameNotFound() {
        when(userService.findByName("Nonexistent User")).thenReturn(Optional.empty());

        ResponseEntity<UserEntity> response = userController.findByName("Nonexistent User");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindByNomeException() {
        when(userService.findByName("Michael Doe")).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<UserEntity> response = userController.findByName("Michael Doe");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
