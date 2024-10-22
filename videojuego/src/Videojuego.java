import excepcion.VidaBajaExcepcion;

public class Videojuego {
    public static void main(String[] args) {
        try {
            Personaje jugador1 = new Personaje("Guerrero", 100);
            Personaje jugador2 = new Personaje("Hechicero", 50);

            System.out.println("Total de personajes creados: " + Personaje.getTotalPersonajes());

            jugador1.recibirDanio();

            Juego.incrementarPuntajeTotal(50);
            System.out.println();
            System.out.println("Puntaje total del juego: " + Juego.getPuntajeTotal());

            String infoPersonaje = String.format("Personaje: %s - Vida: %d", jugador1.getNombre(), jugador1.getVida());
            System.out.println();
            System.out.println(infoPersonaje);

            String infoPersonajeMayusculas = infoPersonaje.toUpperCase();
            System.out.println();
            System.out.println(infoPersonajeMayusculas);

            System.out.println();
            String[] infoPersonajeArr = infoPersonaje.split(" - ");
            for (String parte : infoPersonajeArr) {
                System.out.println(parte);
            }

            Personaje personaje3 = new Personaje("Arquero", 30);
        } catch (VidaBajaExcepcion e) {
            System.err.println();
            System.err.println("¡CUIDADO!: " + e.getMessage());
        }
    }
}