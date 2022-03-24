package br.com.ilia.digital.folhadeponto.objects;

import br.com.ilia.digital.folhadeponto.utilities.repositories.MomentRepository;
import br.com.ilia.digital.folhadeponto.utilities.repositories.RegistryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

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
public class Allocation implements Serializable {
    private String dia;
    private String tempo;
    private String projeto;

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

        LocalTime allocatedTime;
        try {
            allocatedTime = LocalTime.parse(tempo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new Message("Dia em formato inválido, utilize a seguinte formatação: 08:00:00"));
        }

        List<Moment> moments = MomentRepository.getMoments(day.getYear() + "-" + day.getMonthValue() + "-" + day.getDayOfMonth());
        if (moments.size() == 0)
            return ResponseEntity.status(403).body(new Message("Nenhuma batida de ponto registrada na data: " + day));

        int totalHours = 0, totalMinutes = 0, totalSeconds = 0;
        Registry registry = RegistryRepository.getRegistry(day.getYear() + "-" + day.getMonthValue() + "-" + day.getDayOfMonth());

        if (registry.getHorarios().size() == 1)
            return ResponseEntity.status(403).body(new Message("Apenas uma batida foi registrada na data: " + day));
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

        totalHours = totalHours + totalMinutes / 60 + totalSeconds / 60 / 60;
        totalMinutes = totalMinutes % 60 + totalSeconds / 60;
        totalSeconds = totalSeconds % 60;

        int totalAllocatedSeconds = allocatedTime.getHour() * 60 * 60 + allocatedTime.getMinute() * 60 + allocatedTime.getSecond();
        int overallSeconds = totalHours * 60 * 60 + totalMinutes * 60 + totalSeconds;

        LocalTime suggestedTime = LocalTime.of(totalHours,totalMinutes,totalSeconds);

        if (totalAllocatedSeconds > overallSeconds)
            return ResponseEntity.status(400).body(new Message("Não pode alocar tempo maior que o tempo trabalhado no dia. Tempo disponível para alocação: " + suggestedTime.format(DateTimeFormatter.ISO_LOCAL_TIME)));
        setTempo("PT" + totalHours + "H" + totalMinutes + "M" + totalSeconds + "S");

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
}
