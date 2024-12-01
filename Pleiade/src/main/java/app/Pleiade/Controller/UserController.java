package app.Pleiade.Controller;

import app.Pleiade.Entity.UserEntity;
import app.Pleiade.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        UserEntity authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody UserEntity user) {
        try {
            String mesage = this.userService.save(user);
            return new ResponseEntity<>(mesage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody UserEntity user, @PathVariable long id) {
        try {
            String mesage = this.userService.update(user, id);
            return new ResponseEntity<>(mesage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            String mesage = this.userService.delete(id);
            return new ResponseEntity<>(mesage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserEntity>> findAll() {
        try {
            List<UserEntity> lista = this.userService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable long id) {
        try {
            UserEntity user = this.userService.findById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<UserEntity> findByName(@PathVariable String name) {
        try {
            Optional<UserEntity> user = userService.findByName(name);
            if (user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
