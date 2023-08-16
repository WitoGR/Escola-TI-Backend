package escola.ti.controleparental.model.util;

import escola.ti.controleparental.model.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendEmail(EmailDTO toEmail, String body, String subject){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("escola.ti.controle.parental@gmail.com");
        message.setTo(toEmail.getRecepient());
        message.setText(body);
        message.setSubject(subject);

        javaMailSender.send(message);

        return "Mail Sent Successfully...";

    }

}
