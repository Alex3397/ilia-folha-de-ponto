package br.com.ilia.digital.folhadeponto.services.v2;

import br.com.ilia.digital.folhadeponto.objects.Allocation;
import br.com.ilia.digital.folhadeponto.objects.Message;
import br.com.ilia.digital.folhadeponto.objects.Record;
import br.com.ilia.digital.folhadeponto.objects.Registry;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de serviço de Relatórios
 * @version 2
 * @since 2022-03-23 17:27
 */
@Service
@AllArgsConstructor
public class V2RecordService {

    private V2RegistryService registryService;
    private V2AllocationService allocationService;

    /**
     * Função para buscar Relatório mensal com base na data
     * @since 2022-03-23 17:27
     */
    public ResponseEntity<Object> getRecord(String date) {
        if (date == null) return ResponseEntity.status(400).body(new Message("Campo obrigatório não informado: mes"));

        if (date.split("-").length > 2) {

            try {
                int year = Integer.parseInt(date.split("-")[0]);
                if (year < 0)
                    return ResponseEntity.status(400).body(new Message("Formato inválido para campo mes, utilize a seguinte formatação: 2022-01"));
            } catch (Exception e) {
                return ResponseEntity.status(400).body(new Message("Formato inválido para campo mes, utilize a seguinte formatação: 2022-01"));
            }

            try {
                int month = Integer.parseInt(date.split("-")[1]);
                if (month < 0 || month > 12)
                    return ResponseEntity.status(400).body(new Message("Formato inválido para campo mes, utilize a seguinte formatação: 2022-01"));
            } catch (Exception e) {
                return ResponseEntity.status(400).body(new Message("Formato inválido para campo mes, utilize a seguinte formatação: 2022-01"));
            }

            return ResponseEntity.status(400).body(new Message("Formato inválido para campo mes, utilize a seguinte formatação: 2022-01"));
        }

        Record record = new Record();
        updateRecord(record, date);
        return ResponseEntity.status(200).body(record);
    }

    /**
     * Função para atualizar dados de Relatórios mensais com base na data
     * @since 2022-03-23 17:27
     */
    private void updateRecord(Record record, String date) {
        record.setMes(date);

        List<Registry> registryList = new ArrayList<>();
        List<Allocation> allocationList = new ArrayList<>();

        for (Registry registry : registryService.findAll()) {
            if (Integer.parseInt(registry.getDia().split("-")[0]) == Integer.parseInt(date.split("-")[0]) && Integer.parseInt(registry.getDia().split("-")[0]) == Integer.parseInt(date.split("-")[0])) {
                registryList.add(registry);
            }
        }

        for (Allocation allocation : allocationService.findAll()) {
            if (allocation.getDia().contains(date)) {
                allocationList.add(allocation);
            }
        }

        record.setRegistros(registryList);
        record.setAlocacoes(allocationList);
        record.updateDueHours();
        record.updateExceedingHours();
        record.updateWorkedHours();

    }
}
