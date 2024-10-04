import java.util.Map;
import java.util.TreeMap;

public class EjemploImplementacionesMap {
    public static void gestionarDiccionario(Map<String, String> diccionario) {
        diccionario.put("Manzana", "una fruta redonda con piel roja, amarilla o verde");
        diccionario.put("Banana", "una fruta larga y curva con piel amarilla");
        diccionario.put("Naranja", "una fruta redonda con piel gruesa de color naranja");

        diccionario.put("Manzana", "La fruta favorita del Maestro de grado");
    }

    public static void mostrarDiccionario(Map<String, String> diccionario) {
        Map<String, String> diccionarioOrdenado = new TreeMap<>(diccionario);

        System.out.println("Diccionario ordenado:");
        for (Map.Entry<String, String> entrada : diccionarioOrdenado.entrySet()) {
            String palabra = entrada.getKey();
            String definicion = entrada.getValue();
            System.out.println(palabra + ": " + definicion);
        }
    }
}