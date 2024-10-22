public class Juego {
    private static int puntajeTotal = 100;

    public static void incrementarPuntajeTotal(int puntosDeIncremento) {
        System.out.println();
        System.out.println("El puntaje total del juego se ha incrementado en " + puntosDeIncremento + " puntos.");
        puntajeTotal += puntosDeIncremento;
    }

    public static int getPuntajeTotal() {
        return puntajeTotal;
    }
}