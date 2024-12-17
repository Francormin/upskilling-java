package com.example.homeworkorm.exception;

import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo EntityNotFoundException.
     *
     * @param ex Excepción lanzada.
     * @return Respuesta HTTP con el mensaje de error.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        System.out.println("Entró al handleEntityNotFoundException");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<String> handleHibernateException(HibernateException ex) {
        System.out.println("Entró al handleHibernateException");
        System.out.println("Clase de la excepción: " + ex.getClass());
        System.out.println("Mensaje de la excepción: " + ex.getMessage());

        if (ex.getClass().getSimpleName().equals("PropertyValueException")) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
