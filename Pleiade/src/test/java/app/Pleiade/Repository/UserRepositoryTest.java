package app.Pleiade.Repository;

import app.Pleiade.Entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    private User createUser(User data){
        User user = new User();
        this.entityManager.persist(user);
        return user;
    }

    @Test
    @DisplayName("Should get User successfully from DB")
    void findByEmailCase1(){
        User data = new User();
        String email = "john.doe@example.com";

        data.setName("John Doe");
        data.setEmail(email);
        data.setUserName("john123");
        data.setPassword("password123");
        data.setLastName("Doe");
        data.setAccessLevel(0);

        User savedUser = this.userRepository.save(data);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());

        Optional<User> result = this.userRepository.findByEmail(email);

        assertTrue(result.isPresent(), "User should be present");
        assertEquals(email, result.get().getEmail());
    }


    @Test
    void findByName() {
    }
}
