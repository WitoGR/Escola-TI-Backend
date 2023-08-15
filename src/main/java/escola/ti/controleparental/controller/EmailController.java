package escola.ti.controleparental.controller;

import escola.ti.controleparental.model.dto.EmailDTO;
import escola.ti.controleparental.model.util.EmailSenderService;
import escola.ti.controleparental.model.util.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired EmailSenderService emailSenderService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailDTO body){
        Password password = new Password();

       String resposta = emailSenderService.sendEmail(body,password.getPassword(),"Senha Gerada automaticamente");

       return resposta;
    }
}
