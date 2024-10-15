package entities;

/**
 * Clase base abstracta para representar una tarjeta genérica.
 * Contiene los campos comunes para las tarjetas de crédito y débito.
 */
public abstract class Tarjeta {
    private int id;
    private String numero;
    private String titular;

    // Constructor para nuevas tarjetas (sin ID)
    public Tarjeta(String numero, String titular) {
        this.numero = numero;
        this.titular = titular;
    }

    // Constructor para tarjetas existentes (con ID)
    public Tarjeta(int id, String numero, String titular) {
        this.id = id;
        this.numero = numero;
        this.titular = titular;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Tarjeta{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", titular='" + titular + '\'' +
                '}';
    }
}