package escola.ti.controleparental.model;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tb_bloqueio")
@NoArgsConstructor
@AllArgsConstructor
public class BloqueioModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_bloqueio")
    @Getter
    @Setter
    private Integer idBloqueio;

    @Column(name="id_user")
    @Getter
    @Setter
    private Integer idUser;

    @Column(name="url")
    @Getter
    @Setter
    private String url;

    @Column(name="horario_inicio")
    @Getter
    @Setter
    private Time horarioInicio;

    @Column(name="horario_fim")
    @Getter
    @Setter
    private Time horarioFim;

    public String encodeURL(String value){
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public String decodeURL(String value){
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
