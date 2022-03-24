package br.com.ilia.digital.folhadeponto;

import br.com.ilia.digital.folhadeponto.controller.ApiController;
import br.com.ilia.digital.folhadeponto.controller.ApiControllerV2;
import br.com.ilia.digital.folhadeponto.objects.Allocation;
import br.com.ilia.digital.folhadeponto.objects.Moment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

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
     * População rápida da API
     *
     * @since 2022-03-23 17:27
     */

    @Test
    void populate() {
        for (int i = 1; i <= 31; i++) {
            apiController.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,8,25,31).toString()));
            apiController.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,12,50,29).toString()));
            apiController.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,13,10,18).toString()));
            apiController.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,19,59,37).toString()));

            apiController.postAllocation(new Allocation(LocalDate.of(2022,3,i).toString(), LocalTime.of(10,0,0).toString(),"GROGALDR"));
        }
    }

    @Test
    void testV2() {
        for (int i = 1; i <= 31; i++) {
            apiControllerV2.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,8,25,31).toString()));
            apiControllerV2.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,12,50,29).toString()));
            apiControllerV2.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,13,10,18).toString()));
            apiControllerV2.postWorkedTime(new Moment(LocalDateTime.of(2022,3,i,19,59,37).toString()));

            apiControllerV2.postAllocation(new Allocation(LocalDate.of(2022,3,i).toString(), LocalTime.of(10,0,0).toString(),"GROGALDR"));
        }
    }
}
