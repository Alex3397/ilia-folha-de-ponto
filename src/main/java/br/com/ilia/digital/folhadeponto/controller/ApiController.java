package br.com.ilia.digital.folhadeponto.controller;

import br.com.ilia.digital.folhadeponto.objects.Allocation;
import br.com.ilia.digital.folhadeponto.objects.Moment;
import br.com.ilia.digital.folhadeponto.services.MomentService;
import br.com.ilia.digital.folhadeponto.services.RecordServices;
import br.com.ilia.digital.folhadeponto.utilities.repositories.AllocationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
@AllArgsConstructor
public class ApiController {
    /**
     * Controller da API
     * @since 2022-03-23 19:25
     */

    private MomentService momentService;
    private RecordServices recordServices;

    @PostMapping("batidas")
    public ResponseEntity<Object> postWorkedTime(@RequestBody Moment moment) {
        return momentService.registerMoment(moment);
    }

    @PostMapping("alocacoes")
    public ResponseEntity<Object> postAllocation(@RequestBody Allocation allocation) {
        ResponseEntity<Object> response = allocation.selfValidate();
        if (response.getStatusCodeValue() == 400) return response;

        AllocationsRepository.saveAllocation(allocation, allocation.returnDay(), allocation.returnMonth(), allocation.returnYear());
        return ResponseEntity.status(201).body(allocation);
    }

    @GetMapping("folhas-de-ponto/{mes}")
    public ResponseEntity<Object> getRecord(@PathVariable(name = "mes") String date) {
        return recordServices.createRecord(date);
    }

}
