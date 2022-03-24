package br.com.ilia.digital.folhadeponto.services.v1;

import br.com.ilia.digital.folhadeponto.objects.Allocation;
import br.com.ilia.digital.folhadeponto.objects.Message;
import br.com.ilia.digital.folhadeponto.objects.Moment;
import br.com.ilia.digital.folhadeponto.objects.Registry;
import br.com.ilia.digital.folhadeponto.repositories.local.LocalMomentRepository;
import br.com.ilia.digital.folhadeponto.repositories.local.LocalRegistryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class V1AllocationService {

    public ResponseEntity<Object> updateAllocation(Allocation allocation) {

        List<Moment> moments = LocalMomentRepository.getMoments(allocation.returnYear() + "-" + allocation.returnMonth() + "-" + allocation.returnDay());
        if (moments.size() == 0)
            return ResponseEntity.status(403).body(new Message("Nenhuma batida de ponto registrada na data: " + allocation.returnYear() + "-" + allocation.returnMonth() + "-" + allocation.returnDay() ));

        int totalHours = 0, totalMinutes = 0, totalSeconds = 0;
        Registry registry = LocalRegistryRepository.getRegistry(allocation.returnYear() + "-" + allocation.returnMonth() + "-" + allocation.returnDay());

        if (registry.getHorarios().size() == 1)
            return ResponseEntity.status(403).body(new Message("Apenas uma batida foi registrada na data: " + allocation.returnYear() + "-" + allocation.returnMonth() + "-" + allocation.returnDay() ));
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

        int totalAllocatedSeconds = allocation.returnAllocatedTime().getHour() * 60 * 60 + allocation.returnAllocatedTime().getMinute() * 60 + allocation.returnAllocatedTime().getSecond();
        int overallSeconds = totalHours * 60 * 60 + totalMinutes * 60 + totalSeconds;

        LocalTime suggestedTime = LocalTime.of(totalHours,totalMinutes,totalSeconds);

        if (totalAllocatedSeconds > overallSeconds)
            return ResponseEntity.status(400).body(new Message("Não pode alocar tempo maior que o tempo trabalhado no dia. Tempo disponível para alocação: " + suggestedTime.format(DateTimeFormatter.ISO_LOCAL_TIME)));
        allocation.setTempo("PT" + totalHours + "H" + totalMinutes + "M" + totalSeconds + "S");
        return ResponseEntity.ok("ok");
    }
}
