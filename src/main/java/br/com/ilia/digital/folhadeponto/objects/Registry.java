package br.com.ilia.digital.folhadeponto.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Registry implements Serializable {
    private String dia;
    private List<String> horarios;
}
