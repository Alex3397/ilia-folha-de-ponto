package br.com.ilia.digital.folhadeponto.eventListener;

import br.com.ilia.digital.folhadeponto.controller.ApiController;
import br.com.ilia.digital.folhadeponto.controller.ApiControllerV2;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Classe criar para popular a API assim que o processo de startup for terminado
 * @since 2022-03-24 18:37
 */

@Component
@AllArgsConstructor
public class RunAfterStartup {

    private final Logger logger = LoggerFactory.getLogger(RunAfterStartup.class);
    private ApiController apiController;
    private ApiControllerV2 apiControllerV2;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        logger.info("[ RunAfterStartup EventListener runAfterStartup ][ Populate API started ]");
        apiController.populate();
        apiControllerV2.populate();
        logger.info("[ RunAfterStartup EventListener runAfterStartup ][ Populate API ended ]");
    }
}