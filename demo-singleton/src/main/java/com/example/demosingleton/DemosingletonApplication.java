package com.example.demosingleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class DemosingletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemosingletonApplication.class, args);
        /*MiSingleton singleton = MiSingleton.obtenerInstancia();
        singleton.mostrarMensaje();*/
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MiSingleton singleton = context.getBean(MiSingleton.class);
        singleton.mostrarMensaje();
    }

}
