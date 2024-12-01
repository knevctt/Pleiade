package app.Pleiade.Repository;

import app.Pleiade.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByName(String name);

    @Query(value = "select * from users where user_name = ?", nativeQuery = true)
    Optional<UserEntity> findByUsername(String userName);
}
