package utils;

public class NotificationUtils {
    public static void showError(String message) {
        /*
        for (int i = 0; i < 256; i++) {
            System.out.printf("\033[38;5;%dmColor %d  \033[0m", i, i);
            if ((i + 1) % 8 == 0) {
                System.out.println();
            }
        }
        */
        System.out.println("\033[1m\033[38;5;173m\n" + message + "\033[0m");
    }
}