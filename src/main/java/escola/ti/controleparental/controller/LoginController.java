package escola.ti.controleparental.controller;

import escola.ti.controleparental.model.UserModel;
import escola.ti.controleparental.model.dto.EmailDTO;
import escola.ti.controleparental.model.dto.LoginDTO;
import escola.ti.controleparental.model.dto.UserLogin;
import escola.ti.controleparental.model.util.EmailSenderService;
import escola.ti.controleparental.model.util.Password;
import escola.ti.controleparental.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    private UserRepository userRepository;

    UserLogin userLogin = new UserLogin();

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailDTO body){
        Password password = new Password();
        this.userLogin.setSenha(password.getPassword());

        String resposta = emailSenderService.sendEmail(body,"Your Password:\n  "+password.getPassword(),"Senha Gerada automaticamente");

        return resposta;
    }

    @PostMapping(path="/login")
    public String loginUsusario(@RequestBody LoginDTO body){
        List<UserModel> findByEmail = new ArrayList<>();

        for(UserModel u : userRepository.findAll()){
            if(body.getLogin().equals(u.getEmail()) || body.getLogin().equals(u.getTelefone())){
                return "Access Granted";
            }
        }
        return "Access Failed";
    }
}
