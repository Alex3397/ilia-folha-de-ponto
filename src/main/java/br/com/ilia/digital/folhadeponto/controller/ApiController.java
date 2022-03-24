package br.com.ilia.digital.folhadeponto.controller;

import br.com.ilia.digital.folhadeponto.objects.Allocation;
import br.com.ilia.digital.folhadeponto.objects.Moment;
import br.com.ilia.digital.folhadeponto.services.v1.V1AllocationService;
import br.com.ilia.digital.folhadeponto.services.v1.V1MomentService;
import br.com.ilia.digital.folhadeponto.services.v1.V1RecordService;
import br.com.ilia.digital.folhadeponto.repositories.local.LocalAllocationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Controller da API versão 1 utilizando arquivos como repositório
 * @since 2022-03-23 19:25
 */

@RestController
@RequestMapping("v1")
@AllArgsConstructor
public class ApiController {

    private V1MomentService momentService;
    private V1RecordService recordServices;
    private V1AllocationService v1AllocationService;

    @PostMapping("batidas")
    public ResponseEntity<Object> postWorkedTime(@RequestBody Moment moment) {
        return momentService.registerMoment(moment);
    }

    @PostMapping("alocacoes")
    public ResponseEntity<Object> postAllocation(@RequestBody Allocation allocation) {
        ResponseEntity<Object> response = allocation.selfValidate();
        if (response.getStatusCodeValue() == 400) return response;

        ResponseEntity<Object> response2 = v1AllocationService.updateAllocation(allocation);
        if (response2.getStatusCodeValue() != 200) return response2;

        LocalAllocationsRepository.saveAllocation(allocation, allocation.returnDay(), allocation.returnMonth(), allocation.returnYear());
        return ResponseEntity.status(201).body(allocation);
    }

    @GetMapping("folhas-de-ponto/{mes}")
    public ResponseEntity<Object> getRecord(@PathVariable(name = "mes") String date) {
        return recordServices.createRecord(date);
    }

}
