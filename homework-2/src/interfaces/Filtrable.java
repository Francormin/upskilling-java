package interfaces;

@FunctionalInterface
public interface Filtrable<T> {
    boolean cumpleFiltro(T producto);
}