package escola.ti.controleparental.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import escola.ti.controleparental.model.HistoricoModel;
import escola.ti.controleparental.model.dto.historico.HistoricoEnvioDTO;
import escola.ti.controleparental.model.dto.historico.HistoricoPostDTO;
import escola.ti.controleparental.model.dto.user.UserLoginInfoDTO;
import escola.ti.controleparental.repository.HistoricoRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(path="/historico")
public class HistoricoController {
    
    @Autowired
    private HistoricoRepository historicoRepository;

    @GetMapping(path="/all")
    public ResponseEntity<List<HistoricoEnvioDTO>> getUserHistorico(@RequestBody UserLoginInfoDTO body ){
        List<HistoricoEnvioDTO> lista = new ArrayList<HistoricoEnvioDTO>();

        for(HistoricoModel h : historicoRepository.findAll()){
            if(body.getIdUser().equals(h.getIdUser())){
                HistoricoEnvioDTO resposta = new HistoricoEnvioDTO();
                resposta.setHorario(""+h.getHorarioDeAcesso());
                resposta.setUrl(h.decodeURL(h.getUrl()));
                lista.add(resposta);
            }
        }
            
        return new ResponseEntity<List<HistoricoEnvioDTO>>(lista, null, 200);
    }

    @PostMapping(path="/save")
    public ResponseEntity<String> saveUserHistorico(@RequestBody HistoricoPostDTO body) {
        HistoricoModel historicoModel = new HistoricoModel();

        historicoModel.setHorarioDeAcesso(body.getHorario());
        historicoModel.setUrl(historicoModel.encodeURL(body.getUrl()));
        historicoModel.setIdUser(body.getIdUser());

        historicoRepository.save(historicoModel);
        
        return new ResponseEntity<String>("Historico Salvo", null, 200);
    }
    

}
