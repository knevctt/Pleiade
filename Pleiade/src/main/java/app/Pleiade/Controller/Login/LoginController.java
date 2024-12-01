package app.Pleiade.Controller.Login;

import app.Pleiade.Dto.UserRegistrationRequest;
import app.Pleiade.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
//@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UserService pessoaService;

        @PostMapping("loginAuth")
        public ResponseEntity<Map<String, String>> logar(@RequestBody LoginRequest login) {
            try {
                String token = pessoaService.logar(login);
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                return ResponseEntity.ok(response);
            } catch (AuthenticationException ex) {
                System.out.println("Erro de autenticação: " + ex.getMessage());
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }





    @PostMapping("novo-usuario/save")
    public ResponseEntity<HttpStatus> saveNewUser(@RequestBody UserRegistrationRequest user) {
            pessoaService.saveNewUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

}
