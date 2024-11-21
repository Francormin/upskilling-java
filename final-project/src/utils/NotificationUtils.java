package utils;

public class NotificationUtils {
    /*for (int i = 0; i < 256; i++) {
        System.out.printf("\033[38;5;%dmColor %d  \033[0m", i, i);
        if ((i + 1) % 8 == 0) {
            System.out.println();
        }
    }*/

    public static void showOnError(String message) {
        System.out.println("\033[1m\033[38;5;173m\n" + message + "\033[0m");
    }

    public static void showOnSuccess(String message) {
        System.out.println("\033[1m\033[38;5;34m\n" + message + "\033[0m");
    }
}