package dto;

/**
 * Objeto de transferencia de datos (DTO) para representar una tarjeta
 * sin exponer datos sensibles como el ID.
 */
public class TarjetaDTO {
    private String numero;
    private String titular;

    public TarjetaDTO(String numero, String titular) {
        this.numero = numero;
        this.titular = titular;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    @Override
    public String toString() {
        return "TarjetaDTO{" +
                "numero='" + numero + '\'' +
                ", titular='" + titular + '\'' +
                '}';
    }
}