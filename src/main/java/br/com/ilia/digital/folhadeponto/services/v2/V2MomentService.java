package br.com.ilia.digital.folhadeponto.services.v2;

import br.com.ilia.digital.folhadeponto.objects.Message;
import br.com.ilia.digital.folhadeponto.objects.Moment;
import br.com.ilia.digital.folhadeponto.objects.Registry;
import br.com.ilia.digital.folhadeponto.repositories.database.MomentRepository;
import br.com.ilia.digital.folhadeponto.repositories.database.RegistryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class V2MomentService {

    private V2RegistryService v2RegistryService;
    private RegistryRepository registryRepository;
    private MomentRepository momentRepository;

    public ResponseEntity<Object> postWorkedTime(Moment moment) {

        ResponseEntity<Object> response = moment.selfValidate();
        if (response.getStatusCodeValue() == 400) return response;

        Optional<Moment> foundMoment = momentRepository.findByDataHora(moment.getDataHora());
        System.out.println("got moment: " + moment.getDataHora());
        if (foundMoment.isPresent()) return ResponseEntity.status(409).body(new Message("Horário já registrado"));

        DayOfWeek dayOfWeek = LocalDateTime.parse(moment.getDataHora()).getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            return ResponseEntity.status(403).body(new Message("Sábado e domingo não são permitidos como dia de trabalho"));
        momentRepository.save(moment);

        System.out.println("find registry by date: " + moment.getDate());
        Optional<Registry> registry = v2RegistryService.findRegistryByDate(moment.getDate());
        System.out.println(registry.map(value -> "got registry: " + value.getDia()).orElse("registry was not present"));
        List<String> hours;

        if (registry.isPresent()) {
            if (registry.get().getHorarios().size() == 4)
                return ResponseEntity.status(403).body(new Message("Apenas 4 horários podem ser registrados por dia"));

            if (registry.get().getHorarios().size() > 1) {

                LocalTime before = LocalTime.parse(registry.get().getHorarios().get(registry.get().getHorarios().size() - 1));
                LocalTime after = LocalTime.parse(moment.getDataHora().split("T")[1]);

                if (before.isAfter(after))
                    return ResponseEntity.status(403).body(new Message("Não é possível registrar um horário anterior à: " + before));
            }
            if (registry.get().getHorarios().size() == 2) {
                LocalTime beforeLunch = LocalTime.parse(registry.get().getHorarios().get(1));
                LocalTime afterLunch = LocalTime.parse(moment.getDataHora().split("T")[1]);

                if (beforeLunch.isBefore(afterLunch.minusHours(1)))
                    return ResponseEntity.status(403).body(new Message("Deve haver no mínimo 1 hora de almoço"));
            }

            hours = registry.get().getHorarios();
            hours.add(moment.getDataHora().split("T")[1]);
            registry.get().setHorarios(hours);
            registryRepository.save(registry.get());
            return ResponseEntity.status(201).body(registry.get());
        } else {
            hours = new ArrayList<>();

            hours.add(moment.getDataHora().split("T")[1]);
            Registry newRegistry = new Registry(moment.getDate(), hours);

            registryRepository.save(newRegistry);
            return ResponseEntity.status(201).body(newRegistry);
        }
    }
}
