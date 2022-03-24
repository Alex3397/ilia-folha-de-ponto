package br.com.ilia.digital.folhadeponto;

import br.com.ilia.digital.folhadeponto.controller.ApiController;
import br.com.ilia.digital.folhadeponto.controller.ApiControllerV2;
import br.com.ilia.digital.folhadeponto.objects.Allocation;
import br.com.ilia.digital.folhadeponto.objects.Moment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
public class FolhaDePontoApplicationTests {

    @Autowired
    private ApiController apiController;
    @Autowired
    private ApiControllerV2 apiControllerV2;

    /**
     * Teste da API Versão 1 buscando erros
     * @since 2022-03-23 19:25
     */
    @Test
    void testv1() {
        for (int i = 1; i <= 31; i++) {
            ResponseEntity<Object> response = apiController.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,8,25,31).toString()));
            Assertions.assertNotEquals(200,response.getStatusCodeValue());
            Assertions.assertNotEquals(201,response.getStatusCodeValue());
            response = apiController.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,12,50,29).toString()));
            Assertions.assertNotEquals(200,response.getStatusCodeValue());
            Assertions.assertNotEquals(201,response.getStatusCodeValue());
            response = apiController.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,13,10,18).toString()));
            Assertions.assertNotEquals(200,response.getStatusCodeValue());
            Assertions.assertNotEquals(201,response.getStatusCodeValue());
            response = apiController.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,19,59,37).toString()));
            Assertions.assertNotEquals(200,response.getStatusCodeValue());
            Assertions.assertNotEquals(201,response.getStatusCodeValue());

            response = apiController.postAllocation(new Allocation(LocalDate.of(2022,3,i).toString(), LocalTime.of(19,0,0).toString(),"GROGALDR"));
            Assertions.assertNotEquals(200,response.getStatusCodeValue());
        }
    }

    /**
     * Teste da API Versão 2 buscando erros
     * @since 2022-03-23 19:25
     */
    @Test
    void testV2() {
        for (int i = 1; i <= 31; i++) {
            ResponseEntity<Object> response = apiControllerV2.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,8,25,31).toString()));
            Assertions.assertNotEquals(200,response.getStatusCodeValue());
            Assertions.assertNotEquals(201,response.getStatusCodeValue());
            response = apiControllerV2.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,12,50,29).toString()));
            Assertions.assertNotEquals(200,response.getStatusCodeValue());
            Assertions.assertNotEquals(201,response.getStatusCodeValue());
            response = apiControllerV2.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,13,10,18).toString()));
            Assertions.assertNotEquals(200,response.getStatusCodeValue());
            Assertions.assertNotEquals(201,response.getStatusCodeValue());
            response = apiControllerV2.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,19,59,37).toString()));
            Assertions.assertNotEquals(200,response.getStatusCodeValue());
            Assertions.assertNotEquals(201,response.getStatusCodeValue());

            response = apiControllerV2.postAllocation(new Allocation(LocalDate.of(2022,3,i).toString(), LocalTime.of(10,0,0).toString(),"GROGALDR"));
            Assertions.assertNotEquals(200,response.getStatusCodeValue());

            response = apiControllerV2.getRecord("2022-03");
            Assertions.assertEquals(200,response.getStatusCodeValue());
        }
    }
}
