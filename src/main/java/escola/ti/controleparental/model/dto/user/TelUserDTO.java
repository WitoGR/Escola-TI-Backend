package escola.ti.controleparental.model.dto.user;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class TelUserDTO {
    private String telefone;
    private Date dataNascimento;
}
