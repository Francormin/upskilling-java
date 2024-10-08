package interfaces;

@FunctionalInterface
public interface Comparable<T> {
    int compareTo(T object);
}