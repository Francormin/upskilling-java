package com.example.demologging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingControllerWithoutLombock {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingControllerWithoutLombock.class);

    @GetMapping("/log")
    public String log() {
        LOGGER.debug("Esto es un ejemplo de depuración");
        LOGGER.info("Esto es un mensaje informativo");
        LOGGER.warn("Esto es una advertencia");
        LOGGER.error("Esto es un error");
        return "Revisar los logs para ver los diferentes niveles de logging";
    }

}
