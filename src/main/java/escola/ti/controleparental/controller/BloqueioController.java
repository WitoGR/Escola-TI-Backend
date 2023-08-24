package escola.ti.controleparental.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import escola.ti.controleparental.model.BloqueioModel;
import escola.ti.controleparental.model.dto.BloqueioDTO;
import escola.ti.controleparental.model.dto.BloqueioDeleteDTO;
import escola.ti.controleparental.model.dto.BloqueioRespostaDTO;
import escola.ti.controleparental.model.dto.UserLoginInfoDTO;
import escola.ti.controleparental.repository.BloqueioRepository;

@RestController
@RequestMapping(path="/bloqueio")
public class BloqueioController {
    
    @Autowired
    private BloqueioRepository bloqueioRepository;

    @GetMapping(path="/all")
    public ResponseEntity<List<BloqueioRespostaDTO>> allBloqueio(@RequestBody UserLoginInfoDTO body){
        List<BloqueioRespostaDTO> resposta = new ArrayList<BloqueioRespostaDTO>();
        BloqueioRespostaDTO item = new BloqueioRespostaDTO();
        
        for(BloqueioModel b: bloqueioRepository.findAll()){
            if(body.getIdUser() == b.getIdUser()){
                item.setIdBloqueio(b.getIdBloqueio());
                item.setUrl(b.decodeURL(b.getUrl()));
                item.setHorarioInicio(""+b.getHorarioInicio());
                item.setHorarioFim(""+b.getHorarioFim());

                resposta.add(item);
            }
        }

        return new ResponseEntity<List<BloqueioRespostaDTO>>(resposta, null, 200);
    }

    @PostMapping(path="/add")
    public ResponseEntity<String> addBloqueio(@RequestBody BloqueioDTO body){
        BloqueioModel bloqueioModel = new BloqueioModel();

        bloqueioModel.setIdUser(body.getIdUser());
        bloqueioModel.setUrl(bloqueioModel.encodeURL(body.getUrl()));
        bloqueioModel.setHorarioInicio(body.getTempoInicio());
        bloqueioModel.setHorarioFim(body.getTempoFim());

        bloqueioRepository.save(bloqueioModel);

        return new ResponseEntity<String>("Bloqueio salvo", null, 200);
    }

    @PostMapping(path="/delete")
    public ResponseEntity<String> deleteBloqueio(@RequestBody BloqueioDeleteDTO body){
        bloqueioRepository.deleteById(body.getIdBloqueio());

        return new ResponseEntity<String>("URL Desbloqueada", null, 200);
    }

}
