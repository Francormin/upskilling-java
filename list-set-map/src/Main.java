import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // EjemploImplementacionesList.ejecutarEjemploArrayList();
        // EjemploImplementacionesList.ejecutarEjemploLinkedList();

        // Set<String> hashSet = new HashSet<>();
        // EjemploImplementacionesSet.ejecutarAgregacionDeSet(hashSet);
        // EjemploImplementacionesSet.mostrarPaises(hashSet);

        Map<String, String> hashMap = new HashMap<>();
        EjemploImplementacionesMap.gestionarDiccionario(hashMap);
        EjemploImplementacionesMap.mostrarDiccionario(hashMap);
    }
}