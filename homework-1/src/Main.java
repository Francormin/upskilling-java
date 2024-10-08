import piezas.*;

public class Main {
    public static void main(String[] args) {
        PiezaAjedrez rey = new Rey();
        PiezaAjedrez reina = new Reina();
        PiezaAjedrez alfil = new Alfil();
        PiezaAjedrez caballo = new Caballo();
        PiezaAjedrez torre = new Torre();
        PiezaAjedrez peon = new Peon();

        rey.mostrarInformacion();
        System.out.println();
        reina.mostrarInformacion();
        System.out.println();
        alfil.mostrarInformacion();
        System.out.println();
        caballo.mostrarInformacion();
        System.out.println();
        torre.mostrarInformacion();
        System.out.println();
        peon.mostrarInformacion();
    }
}