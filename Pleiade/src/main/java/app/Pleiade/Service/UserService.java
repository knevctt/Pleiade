package app.Pleiade.Service;

import app.Pleiade.Entity.User;
import app.Pleiade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String save(User user){
        this.userRepository.save(user);
        return "User saved sucessfully";
    }

    public String update(User user, Long id){
        user.setId(id);
        this.userRepository.save(user);
        return "User updated sucessfully";
    }

    public String delete(long id){
        this.userRepository.deleteById(id);
        return "User deleted sucessfully";
    }

    public List<User> findAll(){
        List<User> list = this.userRepository.findAll();
        return list;
    }

    public User findById(long id){
        User user = this.userRepository.findById(id).get();
        return user;
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public User authenticate(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            return optionalUser.get();
        } return null;
    }
}
