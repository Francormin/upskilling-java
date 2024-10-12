import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Utilidades<Integer> utilidadesEnteros = new Utilidades<>();
        Utilidades<String> utilidadesStrings = new Utilidades<>();

        List<Integer> listaEnteros = Arrays.asList(6, 8, 5, 2, 3);
        List<String> listaStrings = Arrays.asList("G", "J", "S", "V", "P");

        System.out.println("Lista de enteros:");
        utilidadesEnteros.imprimirElementos(listaEnteros);

        System.out.println("\nCopia de lista de enteros:");
        List<?> copiaEnteros = utilidadesEnteros.copiarElementos(listaEnteros);
        System.out.println(copiaEnteros);

        System.out.println("\nLista de strings:");
        utilidadesStrings.imprimirElementos(listaStrings);

        System.out.println("\nCopia de lista de strings:");
        List<?> copiaStrings = utilidadesStrings.copiarElementos(listaStrings);
        System.out.println(copiaStrings);
    }
}