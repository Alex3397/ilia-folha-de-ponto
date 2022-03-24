package br.com.ilia.digital.folhadeponto.objects;

import br.com.ilia.digital.folhadeponto.repositories.local.LocalAllocationsRepository;
import br.com.ilia.digital.folhadeponto.repositories.local.LocalRegistryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de transferência de dados para enviar relatórios mensais
 * @since 2022-03-24 18:37
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record implements Serializable {

    private String mes;
    private String horasTrabalhadas;
    private String horasExcedentes;
    private String horasDevidas;
    private List<Registry> registros;
    private List<Allocation> alocacoes;

    /**
     * Função para retornar a quantidade total de horas trabalhadas
     * @since 2022-03-24 18:37
     */
    private WorkedHours fetchWorkedHours(List<Registry> registryList) {
        int totalHours = 0, totalMinutes = 0, totalSeconds = 0;
        for (Registry registry : registryList) {
            if (registry.getHorarios() != null || (registry.getHorarios() != null && registry.getHorarios().size() > 1))
                for (int i = 1; i < registry.getHorarios().size(); i++) {
                    String time = registry.getHorarios().get(i);

                    int hour = Integer.parseInt(time.split(":")[0]);
                    int minute = Integer.parseInt(time.split(":")[1]);
                    int second = Integer.parseInt(time.split(":")[2]);

                    String previousTime = registry.getHorarios().get(i - 1);
                    int previousHour = Integer.parseInt(previousTime.split(":")[0]);
                    int previousMinute = Integer.parseInt(previousTime.split(":")[1]);
                    int previousSecond = Integer.parseInt(previousTime.split(":")[2]);

                    if (i != 2) {
                        totalHours += hour - previousHour;
                        totalMinutes += minute - previousMinute;
                        totalSeconds += second - previousSecond;
                    }
                }
        }

        totalHours = totalHours + totalMinutes / 60 + totalSeconds / 60 / 60;
        totalMinutes = totalMinutes % 60 + totalSeconds / 60;
        totalSeconds = totalSeconds % 60;
        return new WorkedHours(totalHours, totalMinutes, totalSeconds);
    }

    /**
     * Função para atualizar as horas de trabalho devidas
     * @since 2022-03-24 18:37
     */
    public void updateDueHours() {
        WorkedHours baseWorkHours = new WorkedHours(220, 0, 0);
        WorkedHours workedHours = fetchWorkedHours(registros);

        if (baseWorkHours.getOverallSeconds() >= workedHours.getOverallSeconds()) setHorasDevidas(new WorkedHours(
                baseWorkHours.getTotalHours() - workedHours.getTotalHours(),
                    baseWorkHours.getTotalMinutes() - workedHours.getTotalMinutes(),
                    baseWorkHours.getTotalSeconds() - workedHours.getTotalMinutes()).toString());
        else setHorasDevidas(new WorkedHours(0,0,0).toString());
    }

    /**
     * Função para atualizar as horas de trabalho excedentes
     * @since 2022-03-24 18:37
     */
    public void updateExceedingHours() {
        WorkedHours baseWorkHours = new WorkedHours(220, 0, 0);
        WorkedHours workedHours = fetchWorkedHours(registros);

        if (baseWorkHours.getOverallSeconds() <= workedHours.getOverallSeconds()) setHorasExcedentes(new WorkedHours(
                workedHours.getTotalHours() - baseWorkHours.getTotalHours(),
                workedHours.getTotalMinutes() - baseWorkHours.getTotalMinutes(),
                workedHours.getTotalSeconds() - baseWorkHours.getTotalMinutes()).toString());
        else setHorasExcedentes(new WorkedHours(0,0,0).toString());
    }

    /**
     * Função para atualizar as horas trabalhadas totais
     * @since 2022-03-24 18:37
     */
    public void updateWorkedHours() {
        WorkedHours workedHours = fetchWorkedHours(registros);
        setHorasTrabalhadas(workedHours.toString());
    }
}
