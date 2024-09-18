package app.Pleiade.Service;

import app.Pleiade.Entity.User;
import app.Pleiade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
}
