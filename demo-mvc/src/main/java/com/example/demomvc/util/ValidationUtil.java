package com.example.demomvc.util;

import com.example.demomvc.model.Transaction;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtil {

    public static void validateFieldsOnCreation(Transaction transaction) throws Exception {
        if (transaction.getId() == null || transaction.getId() <= 0) {
            throw new Exception("El id de la transacción debe ser un número entero mayor a 0.");
        }
        if (transaction.getAmount() == null || transaction.getAmount() < 10 || transaction.getAmount() > 100) {
            throw new Exception("El monto de la transacción debe ser un número entre 10 y 100 inclusive.");
        }
        if (transaction.getDate() == null || !isValidDate(transaction.getDate())) {
            throw new Exception("La fecha de la transacción debe tener el siguiente formato: dd/MM/yyyy");
        }
    }

    public static void validateFieldsOnUpdate(Transaction transaction) throws Exception {
        if (transaction.getId() != null) {
            throw new Exception("El id de la transacción no puede ser modificado.");
        }
        if (transaction.getAmount() != null) {
            if (transaction.getAmount() < 10 || transaction.getAmount() > 100) {
                throw new Exception("El monto de la transacción debe ser un número entre 10 y 100 inclusive.");
            }
        }
        if (transaction.getDescription() != null) {
            if (transaction.getDescription().length() < 5) {
                throw new Exception("La descripción de la transacción debe tener un mínimo de 5 caracteres.");
            }
        }
        if (transaction.getDate() != null) {
            if (!isValidDate(transaction.getDate())) {
                throw new Exception("La fecha de la transacción debe tener el siguiente formato: dd/MM/yyyy");
            }
        }
    }

    private static boolean isValidDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            dtf.parse(date);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

}
