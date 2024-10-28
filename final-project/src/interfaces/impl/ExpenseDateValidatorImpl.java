package interfaces;

import exceptions.InvalidExpenseDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseDateValidatorImpl implements ExpenseDateValidator {
    private final SimpleDateFormat dateFormat;

    public ExpenseDateValidatorImpl() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
    }

    @Override
    public void validateDate(String date) throws InvalidExpenseDateException {
        try {
            Date parsedDate = dateFormat.parse(date);
            if (parsedDate.after(new Date())) {
                throw new InvalidExpenseDateException("La fecha no puede ser futura.");
            }
        } catch (ParseException e) {
            throw new InvalidExpenseDateException("Formato de fecha inválido. Por favor, use dd/MM/yyyy.");
        }
    }
}