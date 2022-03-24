package br.com.ilia.digital.folhadeponto.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Momento da batida de ponto
 *
 * @since 2022-03-23 17:27
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Moment implements Serializable {
    private String dataHora;

    public ResponseEntity<Object> selfValidate() {
        if (dataHora == null)
            return ResponseEntity.status(400).body(new Message("Campo obrigatório não informado: dataHora"));
        try {
            LocalDateTime.parse(dataHora);
            return ResponseEntity.ok().body("ok");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new Message("Data e hora em formato inválido"));
        }
    }

    public String getDay() {
        return String.valueOf(LocalDateTime.parse(dataHora).getDayOfMonth());
    }

    public String getMonth() {
        return String.valueOf(LocalDateTime.parse(dataHora).getMonthValue());
    }
    public String getYear() {
        return String.valueOf(LocalDateTime.parse(dataHora).getYear());
    }

    public String getDate() {return getYear() + "-" + getMonth() + "-" + getDay();}
}
