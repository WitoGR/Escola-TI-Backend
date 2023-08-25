package escola.ti.controleparental.model.dto.notificacao;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class NotificacaoUserDTO {
    private Integer tipoRecebimentoNotificacao;
    private String  nomeRecebimentoNotificacao;
    private Boolean tipoRecebimentoSenha;
    private String  nomeRecebimentoSenha;
}
