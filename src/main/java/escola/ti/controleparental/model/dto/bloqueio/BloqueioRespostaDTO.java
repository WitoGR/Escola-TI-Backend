package escola.ti.controleparental.model.dto.bloqueio;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BloqueioRespostaDTO {
    private Integer idBloqueio;
    private String url;
    private String horarioInicio;
    private String horarioFim;
}
