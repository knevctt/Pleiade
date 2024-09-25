package app.Pleiade.Controller;

import app.Pleiade.Entity.User;
import app.Pleiade.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@example.com");
    }

    @Test
    void testSaveUser_WhenExceptionThrown_ShouldReturnBadRequest() throws Exception {
        when(userService.save(any(User.class))).thenThrow(new RuntimeException("Erro ao salvar usuário"));

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\", \"email\":\"john@example.com\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testUpdateUser_WhenExceptionThrown_ShouldReturnBadRequest() throws Exception {
        when(userService.update(any(User.class), eq(1L))).thenThrow(new RuntimeException("Erro ao atualizar usuário"));

        mockMvc.perform(put("/api/user/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\", \"email\":\"john@example.com\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testDeleteUser_WhenExceptionThrown_ShouldReturnBadRequest() throws Exception {
        when(userService.delete(1L)).thenThrow(new RuntimeException("Erro ao deletar usuário"));

        mockMvc.perform(delete("/api/user/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testFindAllUsers_WhenExceptionThrown_ShouldReturnBadRequest() throws Exception {
        when(userService.findAll()).thenThrow(new RuntimeException("Erro ao buscar todos os usuários"));

        mockMvc.perform(get("/api/user/findAll"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testFindUserById_WhenExceptionThrown_ShouldReturnBadRequest() throws Exception {
        when(userService.findById(1L)).thenThrow(new RuntimeException("Erro ao buscar usuário"));

        mockMvc.perform(get("/api/user/findById/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testSaveUser() throws Exception {
        when(userService.save(any(User.class))).thenReturn("User saved sucessfully");

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\", \"email\":\"john@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User saved sucessfully"));
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.update(any(User.class), eq(1L))).thenReturn("User updated sucessfully");

        mockMvc.perform(put("/api/user/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\", \"email\":\"john@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated sucessfully"));
    }

    @Test
    void testDeleteUser() throws Exception {
        when(userService.delete(1L)).thenReturn("User deleted sucessfully");

        mockMvc.perform(delete("/api/user/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted sucessfully"));
    }

    @Test
    void testFindAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/api/user/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    @Test
    void testFindUserById() throws Exception {
        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/user/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testFindUserByName() throws Exception {
        User user = new User();
        user.setName("John");

        when(userService.findByNome("John")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/user/findByName/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

}
