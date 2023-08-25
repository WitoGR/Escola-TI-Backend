package escola.ti.controleparental.model.dto.bloqueio;

import java.sql.Time;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BloqueioDTO {
    private Integer idUser;
    private String url;
    private Time tempoInicio;
    private Time tempoFim;
}
