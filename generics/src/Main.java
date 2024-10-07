import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> listaEnteros = Arrays.asList(1, 5, 2, 4, 3);
        List<String> listaCadenas = Arrays.asList("Hola", "Mundo", "Generics");

        ElementoUtil<Integer> utilEntero = new ElementoUtil<>();
        ElementoUtil<String> utilString = new ElementoUtil<>();

        // utilEntero.mostrarListado(listaEnteros);
        // utilString.mostrarListado(listaCadenas);

        Integer maximoEntero = utilEntero.encontrarMaximo(listaEnteros);
        System.out.println("El máximo de la lista de enteros es: " + maximoEntero);

        String maximoString = utilString.encontrarMaximo(listaCadenas);
        System.out.println("La palabra con la inicial más alta es: " + maximoString);
    }
}