package app.Pleiade.Service;

import app.Pleiade.Controller.Login.LoginRequest;
import app.Pleiade.Dto.UserRegistrationRequest;
import app.Pleiade.Entity.UserEntity;
import app.Pleiade.Repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtUtils jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private UserEntity user;
    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @PostConstruct
    public void init() {
        logger.info("UserService initialized with repository: {}", repository != null);
        logger.info("UserService initialized with passwordEncoder: {}", passwordEncoder != null);
    }

    @Autowired
    private UserRepository userRepository;

    public String save(UserEntity user){
        this.userRepository.save(user);
        return "User saved sucessfully";
    }

    public String update(UserEntity user, Long id){
        user.setId(id);
        this.userRepository.save(user);
        return "User updated sucessfully";
    }

    public String delete(long id){
        this.userRepository.deleteById(id);
        return "User deleted sucessfully";
    }

    public List<UserEntity> findAll(){
        List<UserEntity> list = this.userRepository.findAll();
        return list;
    }

    public UserEntity findById(long id){
        UserEntity user = this.userRepository.findById(id).get();
        return user;
    }

    public Optional<UserEntity> findByName(String name) {
        return userRepository.findByName(name);
    }

    public UserEntity authenticate(String email, String password) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            return optionalUser.get();
        } return null;
    }

    public String logar(LoginRequest login) {
        var data = repository.findByUsername(login.userName()).get();
        System.out.println(data.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.userName(),
                        login.password()
                )
        );
        UserEntity user = repository.findByUsername(login.userName()).get();
        String jwtToken = jwtService.generateToken(user);

        return jwtToken;
    }

    public void saveNewUser(UserRegistrationRequest user) {
        UserEntity newUser = new UserEntity(
                user.getName(),
                user.getLastName(),
                user.getUserName(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getIsAdmin() ? "admin" : "user"
        );
        repository.save(newUser);
    }

    }
