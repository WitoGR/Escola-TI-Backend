package escola.ti.controleparental.model.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class HistoricoPostDTO {
    private Integer idUser;
    private String url;
    private Timestamp horario;
}
