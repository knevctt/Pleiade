package app.Pleiade.Repository;

import app.Pleiade.Entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("John");
        user.setEmail("john@example.com");
        user.setUserName("john123");  // Defina um nome de usuário
        user.setPassword("password123"); // Defina uma senha
        user.setCountry("USA"); // Defina um país
        user.setInstitutionOrAffiliation("University"); // Defina uma instituição
        userRepository.save(user); // Salve o usuário após inicializar todos os campos obrigatórios
    }

    @Test
    void testFindByEmail() {
        User foundUser = userRepository.findByEmail("john@example.com");
        assertNotNull(foundUser);
        assertEquals("John", foundUser.getName());
    }

    @Test
    void testFindById() {
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("John", foundUser.get().getName());
    }
}
