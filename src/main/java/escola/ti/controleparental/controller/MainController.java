package escola.ti.controleparental.controller;

import escola.ti.controleparental.model.UserModel;
import escola.ti.controleparental.model.dto.EmailUserDTO;
import escola.ti.controleparental.model.dto.TelUserDTO;
import escola.ti.controleparental.model.dto.UpdateUserEmailDTO;
import escola.ti.controleparental.model.dto.UpdateUserTelDTO;
import escola.ti.controleparental.model.util.Password;
import escola.ti.controleparental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/testeSenha")
    public String testeSenha(){
        Password password = new Password();
        return password.getPassword();
    }

    @PostMapping(path="/addE")
    public ResponseEntity<EmailUserDTO> addNewUserE(@RequestBody EmailUserDTO body){
        UserModel u = new UserModel();
        u.setEmail(body.getEmail());
        userRepository.save(u);

        return new ResponseEntity<EmailUserDTO>(body, null, 200);
    }

    @PostMapping(path="/addT")
    public ResponseEntity<TelUserDTO> addNewUserT(@RequestBody TelUserDTO body){
        UserModel u = new UserModel();
        u.setTelefone(body.getTelefone());
        userRepository.save(u);

        return new ResponseEntity<TelUserDTO>(body, null, 200);
    }

    @PostMapping(path="/updateT")
    public ResponseEntity<UpdateUserTelDTO> updateUserTel(@RequestBody UpdateUserTelDTO body){
        UserModel u = userRepository.findById(body.getId()).get();
        u.setTelefone(body.getTelefone());

        userRepository.save(u);

        return new ResponseEntity<UpdateUserTelDTO>(body, null, 200);
    }

    @PostMapping(path="/updateE")
    public ResponseEntity<UpdateUserEmailDTO> updateUserEmail(@RequestBody UpdateUserEmailDTO body){
        UserModel u = userRepository.findById(body.getId()).get();
        u.setEmail(body.getEmail());

        userRepository.save(u);

        return new ResponseEntity<UpdateUserEmailDTO>(body, null, 200);
    }

    @GetMapping(path="user")
    public @ResponseBody Iterable<UserModel> getAllUsers(){
        return userRepository.findAll();
    }
}
