package entities;

/**
 * Clase para representar una tarjeta de crédito, extendiendo la clase base Tarjeta.
 */
public class TarjetaCredito extends Tarjeta {
    private String numero;
    private String titular;

    public TarjetaCredito(String numero, String titular) {
        super(numero, titular);
    }

    public TarjetaCredito(int id, String numero, String titular) {
        super(numero, titular);
    }
}