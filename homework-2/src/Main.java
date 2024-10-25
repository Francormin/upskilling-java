import excepciones.ExcepcionPersonalizada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Ejercicio del círculo
        /*

        Scanner lectura = new Scanner(System.in);
        System.out.print("Ingrese el radio del círculo: ");

        int radio = Integer.parseInt(lectura.next());

        Circulo.calcularDiametro(radio);
        Circulo.calcularCircunsferencia(radio);

        */

        // Ejercicio de manipulación de Strings
        /*

        Scanner lecture = new Scanner(System.in);
        System.out.print("Ingrese una frase: ");

        String frase = lecture.nextLine();
        int indiceFinal = frase.indexOf(" ");

        System.out.println("Cantidad de caracteres: " + frase.length());

        System.out.print("Ingrese una palabra para saber si se encuentra en la frase: ");
        String palabra = lecture.nextLine();
        if (!frase.contains(palabra)) {
            System.out.println("Palabra no encontrada");
        } else {
            System.out.println("Palabra encontrada");
        }

        System.out.println("Mayúsculas: " + frase.toUpperCase());
        System.out.println("Minúsculas: " + frase.toLowerCase());
        System.out.println("Primera palabra: " + frase.substring(0, indiceFinal));
        System.out.println("Nueva frase: " + frase.concat(" -Punto final-"));

        */

        // Ejercicio de métodos estáticos
        /*

        Scanner lectura = new Scanner(System.in);
        int num1;
        int num2;

        System.out.print("Ingrese el primer número para realizar una operación matemática: ");
        try {
            num1 = Integer.parseInt(lectura.next());
        } catch (NumberFormatException e) {
            throw new ExcepcionPersonalizada("Número inválido");
        }

        System.out.print("Ingrese el segundo número para realizar una operación matemática: ");
        try {
            num2 = Integer.parseInt(lectura.next());
        } catch (NumberFormatException e) {
            throw new ExcepcionPersonalizada("Número inválido");
        }

        System.out.print("Ingrese la operación matemática que desee realizar: ");
        switch (lectura.next()) {
            case "suma":
                Calculadora.suma(num1, num2);
                break;
            case "resta":
                Calculadora.resta(num1, num2);
                break;
            case "multiplicacion":
                Calculadora.multiplicacion(num1, num2);
                break;
            case "division":
                try {
                    Calculadora.division(num1, num2);
                } catch (ArithmeticException e) {
                    System.err.println("No se puede dividir un número entre 0");
                }
                break;
            default:
                System.err.println("Operación matemática inválida");
                break;
        }

        */

        // Ejercicio con interfaces
        /*

        List<Producto> productos = new ArrayList<>();
        Producto producto1 = new Producto("Camisa", 22.99, "DW842WN", true);
        Producto producto2 = new Producto("Remera", 12.49, "DA731SP", true);
        Producto producto3 = new Producto("Pantalón jean", 19.99, "DD574AL");
        Producto producto4 = new Producto("Pantalón jogging", 15.49, "DN632LI");
        Producto producto5 = new Producto("Pantalón cargo", 17.49, "XZ127WQ", false);
        Producto producto6 = new Producto("Campera", 29.99, "GT889EK", true);
        Producto producto7 = new Producto("Buzo", 26.49, "AU925HY", false);

        productos.add(producto1);
        productos.add(producto2);
        productos.add(producto3);
        productos.add(producto4);
        productos.add(producto5);
        productos.add(producto6);
        productos.add(producto7);

        System.out.println("Lista de productos: " + productos);

        Collections.sort(productos);

        System.out.println();
        System.out.println("Productos ordenados por precio: " + productos);

        List<Producto> productosConStock = new ArrayList<>();
        List<Producto> productosSinStock = new ArrayList<>();
        for (Producto producto: productos) {
            if (producto.cumpleFiltro(producto))
                productosConStock.add(producto);
            else productosSinStock.add(producto);
        }

        System.out.println();
        System.out.println("Productos con stock: " + productosConStock);

        System.out.println();
        System.out.println("Productos sin stock: " + productosSinStock);

        */
    }
}