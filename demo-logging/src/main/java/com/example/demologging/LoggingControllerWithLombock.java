package com.example.demologging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoggingControllerWithLombock {

    @GetMapping("/log")
    public String log() {
        log.debug("Esto es un ejemplo de depuración");
        log.info("Esto es un mensaje informativo");
        log.warn("Esto es una advertencia");
        log.error("Esto es un error");
        return "Revisar los logs para ver los diferentes niveles de logging";
    }

}
