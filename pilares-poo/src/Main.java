public class Main {
    public static void main(String[] args) {
        Automovil autoFamiliar = new AutoFamiliar();
        Automovil autoDeportivo1 = new AutoDeportivo("Renault", "Clio", 2020, false);
        Automovil autoDeportivo2 = new AutoDeportivo("Fiat");

        autoDeportivo1.setMarca("Ford");
        autoDeportivo1.setModelo("Focus");

        System.out.println("Auto familiar, año de fabricación: " + autoFamiliar.getAnioFabricacion());
        System.out.println("Auto deportivo 1, marca: " + autoDeportivo1.getMarca());
        System.out.println("Auto deportivo 2, modelo: " + autoDeportivo2.getModelo());
    }
}