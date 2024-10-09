public class Calculadora {
    public static void suma(int a, int b) {
        System.out.println("El resultado es: " + (a + b));
    }

    public static void resta(int a, int b) {
        System.out.println("El resultado es: " + (a - b));
    }

    public static void multiplicacion(int a, int b) {
        System.out.println("El resultado es: " + (a * b));
    }

    public static void division(int a, int b) throws RuntimeException {
        float resultadoFloat = (float) a / b;
        System.out.println("Float: " + resultadoFloat);
        System.out.println("El resultado es: " + (a / b));
    }
}