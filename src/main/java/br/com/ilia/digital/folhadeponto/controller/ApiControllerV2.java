package br.com.ilia.digital.folhadeponto.controller;

import br.com.ilia.digital.folhadeponto.objects.Allocation;
import br.com.ilia.digital.folhadeponto.objects.Moment;
import br.com.ilia.digital.folhadeponto.repositories.local.LocalAllocationsRepository;
import br.com.ilia.digital.folhadeponto.services.v1.V1AllocationService;
import br.com.ilia.digital.folhadeponto.services.v1.V1MomentService;
import br.com.ilia.digital.folhadeponto.services.v1.V1RecordService;
import br.com.ilia.digital.folhadeponto.services.v2.V2AllocationService;
import br.com.ilia.digital.folhadeponto.services.v2.V2MomentService;
import br.com.ilia.digital.folhadeponto.services.v2.V2RecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Controller da API versão 2 utilizando banco de dados H2 como repositório
 * @since 2022-03-23 19:25
 */

@RestController
@RequestMapping("v2")
@AllArgsConstructor
public class ApiControllerV2 {

    private V2MomentService v2MomentService;
    private V2RecordService v2RecordService;
    private V2AllocationService v2AllocationService;

    @PostMapping("batidas")
    public ResponseEntity<Object> postWorkedTime(@RequestBody Moment moment) {
        return v2MomentService.postWorkedTime(moment);
    }

    @PostMapping("alocacoes")
    public ResponseEntity<Object> postAllocation(@RequestBody Allocation allocation) {
        return v2AllocationService.postAllocation(allocation);
    }

    @GetMapping("folhas-de-ponto/{mes}")
    public ResponseEntity<Object> getRecord(@PathVariable(name = "mes") String date) {
        return v2RecordService.getRecord(date);
    }

    /**
     * Função para popular a API utilizando banco de dados para armazenar os dados
     * @since 2022-03-24 18:37
     */
    public void populate() {
        for (int i = 1; i <= 31; i++) {
            postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,8,25,31).toString()));
            postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,12,50,29).toString()));
            postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,13,10,18).toString()));
            postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,19,59,37).toString()));

            postAllocation(new Allocation(LocalDate.of(2022,3,i).toString(), LocalTime.of(10,0,0).toString(),"GROGALDR"));
        }
    }

}
