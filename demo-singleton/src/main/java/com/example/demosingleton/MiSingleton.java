package com.example.demosingleton;

public class MiSingleton {

    // Instancia única de la clase
    private static MiSingleton instancia;

    // Constructor privado para evitar instanciación no requerida
    MiSingleton() {

    }

    // Método público y estático para obtener la instancia única
    public static MiSingleton obtenerInstancia() {
        if (instancia == null) {
            instancia = new MiSingleton();
        }
        return instancia;
    }

    public void mostrarMensaje() {
        System.out.println("Hola desde mi Singleton manual!");
    }

}
