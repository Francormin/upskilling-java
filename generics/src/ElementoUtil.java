import java.util.List;

public class ElementoUtil<T extends Comparable<T>> {
    public void mostrarListado(List<T> lista) {
        for (T elemento : lista) {
            System.out.println(elemento);
        }
    }

    public T encontrarMaximo(List<? extends T> lista) {
        T maximo = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            T elemento = lista.get(i);
            // comparar maximo vs elemento
            if (elemento.compareTo(maximo) > 0) {
                maximo = elemento;
            }
        }
        return maximo;
    }
}