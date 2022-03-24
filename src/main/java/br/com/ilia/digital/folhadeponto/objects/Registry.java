package br.com.ilia.digital.folhadeponto.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Registro de horas de um Empregado
 * @since 2022-03-23 17:25
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Registry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dia;
    @ElementCollection
    private List<String> horarios;

    public Registry(String day, List<String> schedules) {
        this.dia = day;
        this.horarios = schedules;
    }
}
