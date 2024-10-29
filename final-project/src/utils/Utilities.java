package utils;

import java.util.List;

public class Utilities {
    public static <T> void printElements(List<T> list) {
        list.forEach(System.out::println);
    }
}