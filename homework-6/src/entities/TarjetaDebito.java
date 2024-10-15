package entities;

/**
 * Clase para representar una tarjeta de débito, extendiendo la clase base Tarjeta.
 */
public class TarjetaDebito extends Tarjeta {
    private String numero;
    private String titular;

    public TarjetaDebito(String numero, String titular) {
        super(numero, titular);
    }

    public TarjetaDebito(int id, String numero, String titular) {
        super(numero, titular);
    }
}