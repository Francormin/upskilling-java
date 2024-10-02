public class EjemploToLowerToUpperCase {
    public static void ejecutarToLowerToUpperCase() {
        String texto = "Hola Mundo";

        String resultadoEnMinusculas = texto.toLowerCase();
        System.out.println("El resultado en minúsculas es: " + resultadoEnMinusculas);

        String resultadoEnMayusculas = texto.toUpperCase();
        System.out.println("El resultado en mayúsculas es: " + resultadoEnMayusculas);
    }
}