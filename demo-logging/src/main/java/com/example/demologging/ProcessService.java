package com.example.demologging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcessService {

    public void process() {
        log.info("Iniciando proceso");
        try {
            // LÓGICA DE NEGOCIO
            log.debug("Procesando datos / llamando servicio");
            if (Math.random() > 0.5) {
                throw new RuntimeException("Error inesperado durante el proceso");
            }
            log.info("Proceso finalizado con éxito");
        } catch (Exception exception) {
            log.error(
                "Error en el proceso: {}, con stack trace: {}",
                exception.getMessage(),
                exception.getStackTrace()
            );
        }
    }

}
