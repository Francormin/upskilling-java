package piezas;

public abstract class PiezaAjedrez {
    private String nombre;
    private String movimiento;

    public PiezaAjedrez(String nombre, String movimiento) {
        this.nombre = nombre;
        this.movimiento = movimiento;
    }

    public void mostrarInformacion() {
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Movimiento: " + this.movimiento);
    }
}