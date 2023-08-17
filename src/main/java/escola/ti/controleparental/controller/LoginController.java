package escola.ti.controleparental.controller;

import escola.ti.controleparental.model.UserModel;
import escola.ti.controleparental.model.dto.EmailDTO;
import escola.ti.controleparental.model.dto.LoginDTO;
import escola.ti.controleparental.model.dto.UserLogin;
import escola.ti.controleparental.model.util.EmailSenderService;
import escola.ti.controleparental.model.util.Password;
import escola.ti.controleparental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//import escola.ti.controleparental.model.dto.SmsDTO;
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;

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
        String resposta = "";

        for(UserModel u : userRepository.findAll()){ // Adere um usuario no banco em uma variavel
            if(body.getRecepient().equals(u.getEmail())){ // Verifica se o email dado existe no banco
                Password password = new Password(); // Gera uma senha aleatoria
                this.userLogin.setSenha(password.getPassword()); // Salva senha na memoria

                resposta = emailSenderService.sendSimpleEmail(body,"Your Password:\n  "+password.getPassword(),"Senha Gerada automaticamente"); // Envia o email
                
                return resposta;// Retorna o que foi gerado
            }
            else
                resposta = "Email não registrado";
        }
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

    int attempt = 0;

    @PostMapping(path="/attempt")
    public String loginUsusario(@RequestBody LoginDTO body){

        if(attempt < 3){
            attempt++;

            for(UserModel u : userRepository.findAll()){
                if((body.getLogin().equals(u.getEmail()) || body.getLogin().equals(u.getTelefone())) && body.getSenha().equals(this.userLogin.getSenha())){
                    this.userLogin.setSenha(null);
                    return "Access Granted";
                }
                else return "Access Denied";
            }    
        }
        
        return "Too many failed attempts";
    }
}
