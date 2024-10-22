import excepcion.VidaBajaExcepcion;

public class Personaje {
    private String nombre;
    private int vida;
    private static int totalPersonajes = 0;

    public Personaje(String nombre, int vida) throws VidaBajaExcepcion {
        if (vida <= 30) {
            throw new VidaBajaExcepcion("La vida del personaje " + nombre + " es demasiado baja: " + vida);
        }
        this.nombre = nombre;
        this.vida = vida;
        totalPersonajes++;
    }

    public void recibirDanio() {
        System.out.println();
        System.out.println("El personaje " + this.nombre + " ha recibido 20 puntos de daño. ¡Su vida ha disminuido!");
        this.vida -= 20;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public static int getTotalPersonajes() {
        return totalPersonajes;
    }
}