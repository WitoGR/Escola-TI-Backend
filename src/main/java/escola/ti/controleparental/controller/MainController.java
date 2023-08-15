package escola.ti.controleparental.controller;

import escola.ti.controleparental.model.UserModel;
import escola.ti.controleparental.model.dto.DeleteUserDTO;
import escola.ti.controleparental.model.dto.EmailUserDTO;
import escola.ti.controleparental.model.dto.TelUserDTO;
import escola.ti.controleparental.model.dto.UpdateUserEmailDTO;
import escola.ti.controleparental.model.dto.UpdateUserTelDTO;
import escola.ti.controleparental.model.util.Password;
import escola.ti.controleparental.repository.UserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
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

    // TELEFONE

    // CREATE
    @PostMapping(path="/addT") // Define o caminho onde vai ser feito a requesição (no caso localhost:8080/addT)
    public ResponseEntity<TelUserDTO> addNewUserT(@RequestBody TelUserDTO body){ 
        body.getDataNascimento().setDate(body.getDataNascimento().getDate()+1); // Tive que fazer isso pois estava sempre definindo um dia antes

        UserModel u = new UserModel(); // Cria um objeto do tipo UserModel, o user model é uma modelo de como é no banco, para não ter problema de tipos
        u.setTelefone(body.getTelefone()); // Se define o que for nescessario no objeto que foi iniciada a cima
        u.setDataNascimento(body.getDataNascimento());
        // ...
        userRepository.save(u); // salva o objeto modelo no banco

        return new ResponseEntity<TelUserDTO>(body, null, 200); // O retorno ao usuario, trazendo de volta o que foi enviado e o protocolo HTTP em JSON

        /*  É feito um caminho em um Post
            inicio > pathing(PostMapping) > RequestBody(o que vai ser enviado pelo usuario) > ResponseEntity (o que o back vai trazer de resposta ao usuario)

            O usuario envia uma informação no path definido, essa informação é armazenada no RequestBody, essa informação é utilizada pela função e salva/atualizada/deletada
            no banco utilizando o repository, e uma resposta é enviada de volta ao usuario/front (objeto, headers, protocoloHTTP)
         */
    }

    // UPDATE

    @PostMapping(path="/updateT")
    public ResponseEntity<UpdateUserTelDTO> updateUserTel(@RequestBody UpdateUserTelDTO body){
        UserModel u = userRepository.findById(body.getId()).get();// Salva as informações do banco no objeto (id/email/telefone)
        u.setTelefone(body.getTelefone()); // Modifica o valor do objeto

        userRepository.save(u); // Salva de volta no banco, ja que no objeto ja tem o id ele atualiza aquele id no banco, assim sendo o mesmo comando de criação

        return new ResponseEntity<UpdateUserTelDTO>(body, null, 200);
    }

    // EMAIL

    @PostMapping(path="/addE")
    public ResponseEntity<EmailUserDTO> addNewUserE(@RequestBody EmailUserDTO body){
        body.getDataNascimento().setDate(body.getDataNascimento().getDate()+1);

        UserModel u = new UserModel();
        u.setEmail(body.getEmail());
        u.setDataNascimento(body.getDataNascimento());

        userRepository.save(u);

        return new ResponseEntity<EmailUserDTO>(body, null, 200);
    }

    @PostMapping(path="/updateE")
    public ResponseEntity<UpdateUserEmailDTO> updateUserEmail(@RequestBody UpdateUserEmailDTO body){
        UserModel u = userRepository.findById(body.getId()).get();
        u.setEmail(body.getEmail()); 

        userRepository.save(u);

        return new ResponseEntity<UpdateUserEmailDTO>(body, null, 200);
    }

    // DELETE

    @PostMapping(path="/deleteU")
    public ResponseEntity<DeleteUserDTO> deleteUser(@RequestBody DeleteUserDTO body){
        userRepository.deleteById(body.getId()); // Comando de delete do repository, o usuario informa o ID e ja podemos excluir do banco.

        return new ResponseEntity<DeleteUserDTO>(body, null, 200); // Retorno de confirmação.
    }

    @GetMapping(path="/user")
    public @ResponseBody Iterable<UserModel> getAllUsers(){
        return userRepository.findAll();
    }

    // EMAIL
    

}
