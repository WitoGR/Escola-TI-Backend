package escola.ti.controleparental.model;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // Define que é uma entidade para o hibernate
@Table(name = "tb_user") // Define que tabela que vai estar no banco de dados
@NoArgsConstructor
@AllArgsConstructor // Gera um construtor vazio e outro com todas as informações
public class UserModel {

    @Id // Define que é um identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ativa o auto incremento
    @Column(name = "id_user") // Define a coluna na tabela definida lá em cima
    @Getter
    @Setter // Gera automaticamente e implicitamento o getter e setter
    private Integer idUser;

    @Column(name = "email")
    @Basic(optional = true)
    @Getter
    @Setter
    private String email;

    @Column(name = "telefone")
    @Basic(optional = true)
    @Getter
    @Setter
    private String telefone;

    @Column(name="data_nascimento")
    @Basic(optional = false)
    @Getter
    @Setter
    private Date dataNascimento;

    @Column(name="tipo_recebimento_senha")
    @Basic(optional = false)
    @Getter
    @Setter
    private Boolean tipoRecebimentoSenha;

    @Column(name="tipo_recebimento_notificacao")
    @Basic(optional = false)
    @Getter
    @Setter
    private Integer tipoRecebimentoNotificação;
}
