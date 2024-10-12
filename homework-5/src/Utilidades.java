import java.util.ArrayList;
import java.util.List;

public class Utilidades<T> {
    public void imprimirElementos(List<T> lista) {
        for (T elemento : lista) {
            System.out.println(elemento);
        }
    }

    public List<? super T> copiarElementos(List<T> lista) {
        List<T> nuevaLista = new ArrayList<>(lista);
        return nuevaLista;
    }
}