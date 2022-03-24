package br.com.ilia.digital.folhadeponto.objects;

import br.com.ilia.digital.folhadeponto.repositories.local.LocalMomentRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Alocação de um Empregado
 *
 * @since 2022-03-23 17:22
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Allocation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dia;
    private String tempo;
    private String projeto;

    public Allocation(String dia, String tempo, String projeto) {
        this.dia = dia;
        this.tempo = tempo;
        this.projeto = projeto;
    }

    public ResponseEntity<Object> selfValidate() {
        if (dia == null) return ResponseEntity.status(400).body(new Message("Campo obrigatório não informado: dia"));
        if (tempo == null)
            return ResponseEntity.status(400).body(new Message("Campo obrigatório não informado: tempo"));
        if (projeto == null)
            return ResponseEntity.status(400).body(new Message("Campo obrigatório não informado: projeto"));

        LocalDate day;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            day = LocalDate.parse(dia, formatter);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new Message("Dia em formato inválido, utilize a seguinte formatação: 2022-03-23"));
        }

        try {
            LocalTime.parse(tempo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new Message("Dia em formato inválido, utilize a seguinte formatação: 08:00:00"));
        }

        return ResponseEntity.ok("ok");
    }

    public String returnDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.valueOf(LocalDate.parse(dia,formatter).getDayOfMonth());
    }

    public String returnMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.valueOf(LocalDate.parse(dia,formatter).getMonthValue());
    }
    public String returnYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.valueOf(LocalDate.parse(dia,formatter).getYear());
    }

    public LocalTime returnAllocatedTime() {
        return LocalTime.parse(tempo);
    }
}
