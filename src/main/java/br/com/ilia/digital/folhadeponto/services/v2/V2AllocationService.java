package br.com.ilia.digital.folhadeponto.services.v2;

import br.com.ilia.digital.folhadeponto.objects.Allocation;
import br.com.ilia.digital.folhadeponto.objects.Message;
import br.com.ilia.digital.folhadeponto.objects.Registry;
import br.com.ilia.digital.folhadeponto.repositories.database.AllocationsRepository;
import br.com.ilia.digital.folhadeponto.repositories.local.LocalAllocationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class V2AllocationService {

    private V2RegistryService registryService;
    private AllocationsRepository allocationsRepository;

    public ResponseEntity<Object> postAllocation(Allocation allocation) {
        ResponseEntity<Object> response = allocation.selfValidate();
        if (response.getStatusCodeValue() == 400) return response;

        ResponseEntity<Object> response2 = updateAllocation(allocation);
        if (response2.getStatusCodeValue() != 200) return response2;

        LocalAllocationsRepository.saveAllocation(allocation, allocation.returnDay(), allocation.returnMonth(), allocation.returnYear());
        return ResponseEntity.status(201).body(allocation);
    }

    private ResponseEntity<Object> updateAllocation(Allocation allocation) {
        if (allocationsRepository.findByDia(allocation.getDia()).isPresent()) return ResponseEntity.status(403).body(new Message("Alocação já registrada na data: " + allocation.getDia()));

        int totalHours = 0, totalMinutes = 0, totalSeconds = 0;
        Optional<Registry> registry = registryService.findRegistryByDate(allocation.returnYear() + "-" + allocation.returnMonth() + "-" + allocation.returnDay());

        if (registry.isEmpty()) return ResponseEntity.status(404).body(new Message("Não foi encontrada nenhum registro na data: " + allocation.returnYear() + "-" + allocation.returnMonth() + "-" + allocation.returnDay() ));

        if (registry.get().getHorarios().size() == 1)
            return ResponseEntity.status(403).body(new Message("Apenas uma batida foi registrada na data: " + allocation.returnYear() + "-" + allocation.returnMonth() + "-" + allocation.returnDay() ));
        for (int i = 1; i < registry.get().getHorarios().size(); i++) {
            String time = registry.get().getHorarios().get(i);

            int hour = Integer.parseInt(time.split(":")[0]);
            int minute = Integer.parseInt(time.split(":")[1]);
            int second = Integer.parseInt(time.split(":")[2]);

            String previousTime = registry.get().getHorarios().get(i - 1);
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
        allocationsRepository.save(allocation);
        return ResponseEntity.status(201).body(allocation);
    }

}
