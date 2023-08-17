package escola.ti.controleparental.controller;

import escola.ti.controleparental.model.UserModel;
import escola.ti.controleparental.model.dto.EmailDTO;
import escola.ti.controleparental.model.dto.LoginDTO;
import escola.ti.controleparental.model.dto.SmsDTO;
import escola.ti.controleparental.model.dto.UserLogin;
import escola.ti.controleparental.model.util.EmailSenderService;
import escola.ti.controleparental.model.util.Password;
import escola.ti.controleparental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
@RequestMapping(path="/login")
public class LoginController {  
    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    private UserRepository userRepository;

    UserLogin userLogin = new UserLogin();

    @GetMapping(path="/teste")
    public String teste(){  
        return "funcionando";
    }

    // EMAIL --------------------------------------------

    @PostMapping(path="/sendEmail")
    public String sendEmail(@RequestBody EmailDTO body){
        Password password = new Password();
        this.userLogin.setSenha(password.getPassword());

        String resposta = emailSenderService.sendSimpleEmail(body,"Your Password:\n  "+password.getPassword(),"Senha Gerada automaticamente");

        return resposta;
    }

    // SMS ----------------------------------------------
    
    // PRECISA PAGAR UM TELEFONE NO TWILIO

    /* 
    @PostMapping(path="/sendSMS")
    public String sendSMS(@RequestBody SmsDTO body){
        Password senha = new Password();

        Twilio.init("AC1c299e68995ad32589856454d9989193", "829f9de2a0b3cca5edc754595001f0e4");

        Message message = Message.creator(new PhoneNumber(body.getTelefone()),new PhoneNumber("MODIFICAR COM NUMERO PAGO"),"Your password: "+senha.getPassword()).create();

        return "SMS sent successfully";
    }
    */

    @PostMapping(path="/login")
    public String loginUsusario(@RequestBody LoginDTO body){
        for(UserModel u : userRepository.findAll()){
            if(body.getLogin().equals(u.getEmail()) || body.getLogin().equals(u.getTelefone())){
                return "Access Granted";
            }
        }
        return "Access Failed";
    }
}
