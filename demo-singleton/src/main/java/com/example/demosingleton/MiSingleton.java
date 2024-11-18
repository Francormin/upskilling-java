package com.example.demosingleton;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component // Para especificarle a Spring que debe tratar a esta clase como un Bean
@Scope("singleton") // Por defecto, el scope de los beans que Spring administra es Singleton
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
