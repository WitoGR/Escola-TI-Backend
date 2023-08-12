package escola.ti.controleparental.controller;

import com.fasterxml.jackson.core.JsonParser;
import escola.ti.controleparental.model.UserModel;
import escola.ti.controleparental.model.util.Password;
import escola.ti.controleparental.repository.UserRepository;
import org.apache.catalina.User;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/testeSenha")
    public String oi(){
        Password password = new Password();
        return password.getPassword();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser(@RequestParam String email){
        UserModel u = new UserModel();
        u.setEmail(email);
        userRepository.save(u);

        return "Saved";
    }

    @GetMapping(path="user")
    public @ResponseBody Iterable<UserModel> getAllUsers(){
        return userRepository.findAll();
    }
}
