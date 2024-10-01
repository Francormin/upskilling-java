public class AutoFamiliar extends Automovil {
    private int cantAsientos;

    public AutoFamiliar() {
    }

    public AutoFamiliar(String marca, String modelo, int anioFabricacion, int cantAsientos) {
        super(marca, modelo, anioFabricacion);
        this.cantAsientos = cantAsientos;
    }

    @Override
    public void acelerar() {
        System.out.println("Acelera despacio, lleva bebé a bordo");
    }

    public int getCantAsientos() {
        return cantAsientos;
    }

    public void setCantAsientos(int cantAsientos) {
        this.cantAsientos = cantAsientos;
    }
}