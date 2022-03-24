package br.com.ilia.digital.folhadeponto.controller;

import br.com.ilia.digital.folhadeponto.objects.Allocation;
import br.com.ilia.digital.folhadeponto.objects.Moment;
import br.com.ilia.digital.folhadeponto.repositories.local.LocalAllocationsRepository;
import br.com.ilia.digital.folhadeponto.services.v1.V1MomentService;
import br.com.ilia.digital.folhadeponto.services.v1.V1RecordService;
import br.com.ilia.digital.folhadeponto.services.v2.V2MomentService;
import br.com.ilia.digital.folhadeponto.services.v2.V2RecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller da API versão 2 utilizando banco de dados H2 como repositório
 * @since 2022-03-23 19:25
 */

@RestController
@RequestMapping("v2")
@AllArgsConstructor
public class ApiControllerV2 {

    private V1MomentService momentService;
    private V2MomentService v2MomentService;
    private V1RecordService recordServices;
    private V2RecordService v2RecordService;

    @PostMapping("batidas")
    public ResponseEntity<Object> postWorkedTime(@RequestBody Moment moment) {
        return v2MomentService.postWorkedTime(moment);
    }

    @PostMapping("alocacoes")
    public ResponseEntity<Object> postAllocation(@RequestBody Allocation allocation) {
        ResponseEntity<Object> response = allocation.selfValidate();
        if (response.getStatusCodeValue() == 400) return response;

        LocalAllocationsRepository.saveAllocation(allocation, allocation.returnDay(), allocation.returnMonth(), allocation.returnYear());
        return ResponseEntity.status(201).body(allocation);
    }

    @GetMapping("folhas-de-ponto/{mes}")
    public ResponseEntity<Object> getRecord(@PathVariable(name = "mes") String date) {
        return recordServices.createRecord(date);
    }

}
