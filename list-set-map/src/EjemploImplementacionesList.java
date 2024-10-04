import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EjemploImplementacionesList {
    public static void ejecutarEjemploArrayList() {
        List<String> arrayList = new ArrayList<>();

        arrayList.add("Manzana"); // índice 0
        arrayList.add("Banana"); // índice 1
        arrayList.add(2, "Naranja"); // índice explícito 2

        String primerElemento = arrayList.get(0);
        String segundoElemento = arrayList.get(1);

        System.out.println("Elemento 1: " + primerElemento);
        System.out.println("Elemento 2: " + segundoElemento);

        System.out.println();
        System.out.println("Iteración con bucle for");
        for (String elemento : arrayList) {
            System.out.println("Elemento: " + elemento);
        }

        System.out.println();
        boolean contieneNaranja = arrayList.contains("Naranja");
        System.out.println("¿El ArrayList contiene Naranja?: " + contieneNaranja);

        System.out.println();
        int cantidadElementos = arrayList.size();
        System.out.println("Tamaño del ArrayList: " + cantidadElementos);
    }

    public static void ejecutarEjemploLinkedList() {
        List<Integer> linkedList = new LinkedList<>();

        linkedList.add(10);
        linkedList.add(20);
        linkedList.add(30);

        linkedList.remove(Integer.valueOf(20));

        System.out.println("BUCLE DE LINKEDLIST");
        for (Integer num : linkedList) {
            System.out.println(num);
        }

        System.out.println();
        boolean contiene20 = linkedList.contains(20);
        System.out.println("¿La LinkedList contiene el número 20?: " + contiene20);

        System.out.println();
        int cantidadElementos = linkedList.size();
        System.out.println("Cantidad de elementos: " + cantidadElementos);
    }
}