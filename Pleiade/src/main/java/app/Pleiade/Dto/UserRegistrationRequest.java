package app.Pleiade.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {
    private String name;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private Boolean isAdmin = false;
}
