package br.com.ilia.digital.folhadeponto.services;

import br.com.ilia.digital.folhadeponto.objects.Message;
import br.com.ilia.digital.folhadeponto.objects.Record;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RecordServices {

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
        record.gatherDataByDate(date);
        return ResponseEntity.status(200).body(record);
    }
}