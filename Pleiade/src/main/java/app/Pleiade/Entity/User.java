package app.Pleiade.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull//0 for user 1 for admin 2 for dev access
    private int accessLevel;

    @NotBlank
    private String name;

    private String lastName;

    @NotBlank
    private String userName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String institutionOrAffiliation;

    @NotBlank
    private String country;
}
