package br.com.ilia.digital.folhadeponto.services.v1;

import br.com.ilia.digital.folhadeponto.objects.Allocation;
import br.com.ilia.digital.folhadeponto.objects.Message;
import br.com.ilia.digital.folhadeponto.objects.Record;
import br.com.ilia.digital.folhadeponto.objects.Registry;
import br.com.ilia.digital.folhadeponto.repositories.local.LocalAllocationsRepository;
import br.com.ilia.digital.folhadeponto.repositories.local.LocalRegistryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de serviço de Relatórios
 * @version 1
 * @since 2022-03-23 17:27
 */

@Service
public class V1RecordService {

    /**
     * Função para reunir dados com base na data
     * @since 2022-03-23 17:27
     */
    public void gatherDataByDate(String date, Record record) {
        Path configPath = System.getProperty("os.name").contains("Windows") ? Path.of(System.getProperty("user.dir"), "\\config") : Path.of(System.getProperty("user.dir"), "/config");
        File configDir = new File(String.valueOf(configPath));
        record.setMes(date);

        if (!configDir.exists()) configDir.mkdirs();
        String[] dateParts = date.split("-");
        String[] filesList = configDir.list();

        List<Registry> registryList = new ArrayList<>();
        List<Allocation> allocationList = new ArrayList<>();

        if (filesList != null && filesList.length > 0) for (String file : filesList) {
            if (file.contains(String.valueOf(Integer.parseInt(dateParts[0]))) && file.contains(String.valueOf(Integer.parseInt(dateParts[1])))) {
                if (file.contains("registry"))
                    registryList.add(LocalRegistryRepository.getRegistry(file.replace("registry", "")));
                if (file.contains("allocation"))
                    allocationList.add(LocalAllocationsRepository.getAllocation(file.replace("allocation", "")));
            }
        }

        record.setAlocacoes(allocationList);
        record.setRegistros(registryList);

        record.updateWorkedHours();
        record.updateExceedingHours();
        record.updateDueHours();
    }

    /**
     * Função para criar Relatório
     * @since 2022-03-23 17:27
     */
    public ResponseEntity<Object> createRecord(String date) {
        if (date == null) return ResponseEntity.status(400).body(new Message("Campo obrigatório não informado: mes"));

        if (date.split("-").length > 2) {

            try {
                int year = Integer.parseInt(date.split("-")[0]);
                if (year < 0) return ResponseEntity.status(400).body(new Message("Formato inválido para campo mes, utilize a seguinte formatação: 2022-01"));
            } catch (Exception e) {
                return ResponseEntity.status(400).body(new Message("Formato inválido para campo mes, utilize a seguinte formatação: 2022-01"));
            }

            try {
                int month = Integer.parseInt(date.split("-")[1]);
                if (month < 0 || month > 12) return ResponseEntity.status(400).body(new Message("Formato inválido para campo mes, utilize a seguinte formatação: 2022-01"));
            } catch (Exception e) {
                return ResponseEntity.status(400).body(new Message("Formato inválido para campo mes, utilize a seguinte formatação: 2022-01"));
            }

            return ResponseEntity.status(400).body(new Message("Formato inválido para campo mes, utilize a seguinte formatação: 2022-01"));
        }

        Record record = new Record();
        gatherDataByDate(date, record);
        return ResponseEntity.status(200).body(record);
    }
}
