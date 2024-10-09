public class Circulo {
    private static final double PI = 3.141592;

    public static void calcularDiametro(int radio) {
        System.out.println("El diámetro del círculo es: " + radio * 2);
    }

    public static void calcularCircunsferencia(int radio) {
        System.out.println("La circunsferencia del círculo es: " + (radio * 2) * PI);
    }
}