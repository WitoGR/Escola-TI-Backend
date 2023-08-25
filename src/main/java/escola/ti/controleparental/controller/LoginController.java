package escola.ti.controleparental.controller;

import escola.ti.controleparental.model.UserModel;
import escola.ti.controleparental.model.dto.user.EmailDTO;
import escola.ti.controleparental.model.dto.user.LoginDTO;
import escola.ti.controleparental.model.dto.user.UserLogin;
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

    @GetMapping(path="/testeSenha")
    public ResponseEntity<String> testeSenha(){ // gera uma senha e salva no local, para nao ter que esperar o envio da senha por email/telefone;
        Password p = new Password();
        userLogin.setSenha(p.getPassword());
        return new ResponseEntity<String>(userLogin.getSenha(), null, 200);
    }

    // EMAIL --------------------------------------------

    @PostMapping(path="/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO body){
        String resposta = "";
        Integer httpStatus = 403;

        for(UserModel u : userRepository.findAll()){ // Adere um usuario no banco em uma variavel
            if(body.getRecepient().equals(u.getEmail())){ // Verifica se o email dado existe no banco
                Password password = new Password(); // Gera uma senha aleatoria
                this.userLogin.setSenha(password.getPassword()); // Salva senha na memoria

                resposta = emailSenderService.sendSimpleEmail(body,"Your Password:\n  "+password.getPassword(),"Senha Gerada automaticamente"); // Envia o email
                
                return new ResponseEntity<String>(resposta, null, httpStatus=200);// Retorna o que foi gerado
            }
            else
                resposta = "Email não registrado";
        }
        return new ResponseEntity<String>(resposta, null, httpStatus);
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
    public ResponseEntity<String> loginUsusario(@RequestBody LoginDTO body){
        String response = "Error"; // Naturalmente nunca deve trazer essa resposta
        Integer httpStatus = 403;

        if(attempt < 3){
            attempt++; // Aumenta o numero de tentativas

            for(UserModel u : userRepository.findAll()){ 
                if((body.getLogin().equals(u.getEmail()) || body.getLogin().equals(u.getTelefone())) && body.getSenha().equals(this.userLogin.getSenha())){ // Valida o login e a senha enviada pro login
                    this.userLogin.setSenha(null); // Reseta a senha para nao deixar armazenada
                    return new ResponseEntity<String>("Sucesso!", null, httpStatus=200); // Resposta de validação
                }
                else response = "Access Denied"; // Resposta de acesso negado
            }    
        }
        else{
            response = "muitas tentativas falhas"; // Resposta de 3 tentativas consecutivas erradas (Adicionar envio de email com muitas tentativas erradas) 
            httpStatus = 423;
        } 
        
        return new ResponseEntity<String>(response, null, httpStatus); // retorna a resposta
    }
}
