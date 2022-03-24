package br.com.ilia.digital.folhadeponto.services;

import br.com.ilia.digital.folhadeponto.objects.Message;
import br.com.ilia.digital.folhadeponto.objects.Moment;
import br.com.ilia.digital.folhadeponto.objects.Registry;
import br.com.ilia.digital.folhadeponto.repositories.local.LocalMomentRepository;
import br.com.ilia.digital.folhadeponto.repositories.local.LocalRegistryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MomentService {
    /**
     * Serviço de Momento
     * Verifica e cria momento
     *
     * @since 2022-03-23 17:22
     */

    public ResponseEntity<Object> registerMoment(Moment moment) {

        ResponseEntity<Object> response = moment.selfValidate();
        if (response.getStatusCodeValue() == 400) return response;

        DayOfWeek dayOfWeek = LocalDateTime.parse(moment.getDataHora()).getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            return ResponseEntity.status(403).body(new Message("Sábado e domingo não são permitidos como dia de trabalho"));

        List<Moment> momentList = LocalMomentRepository.getMoments(moment.getDate());
        if (momentList.size() == 4)
            return ResponseEntity.status(403).body(new Message("Apenas 4 horários podem ser registrados por dia"));

        if (momentList.size() > 1) {

            LocalDateTime before = LocalDateTime.parse(momentList.get(momentList.size() - 1).getDataHora());
            LocalDateTime after = LocalDateTime.parse(moment.getDataHora());

            if (before.isAfter(after))
                return ResponseEntity.status(403).body(new Message("Não é possível registrar um horário anterior à: " + before));
        }
        if (momentList.size() == 2) {
            LocalDateTime beforeLunch = LocalDateTime.parse(momentList.get(1).getDataHora());
            LocalDateTime afterLunch = LocalDateTime.parse(moment.getDataHora());

            if (beforeLunch.isBefore(afterLunch.minusHours(1)))
                return ResponseEntity.status(403).body(new Message("Deve haver no mínimo 1 hora de almoço"));
        }


        Moment foundMoment = LocalMomentRepository.findByDateTime(moment.getDataHora(), moment.getDate());
        if (foundMoment != null) return ResponseEntity.status(409).body(new Message("Horário já registrado"));

        LocalMomentRepository.saveMoment(moment);
        Registry registry = new Registry(moment.getDay(), LocalMomentRepository.getSchedules(moment.getDate()));
        LocalRegistryRepository.saveRegistry(registry, moment.getDay(), moment.getMonth(), moment.getYear());
        return ResponseEntity.status(201).body(registry);
    }
}
