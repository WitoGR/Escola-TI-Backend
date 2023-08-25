package escola.ti.controleparental.model.dto.user;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class EmailUserDTO {
    private String email;
    private Date dataNascimento;
}
