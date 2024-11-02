package com.example.homeworkspringboot;

import com.example.homeworkspringboot.model.Task;
import com.example.homeworkspringboot.service.TaskService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }

    @Bean
    CommandLineRunner run(TaskService taskService) {
        return args -> {
            // Crear nuevas tareas
            Task task1 = new Task();
            task1.setTitle("Start Spring Boot Project");
            task1.setDescription("Initialize the Spring Boot practice project.");
            task1.setDueDate(new Date());

            Task task2 = new Task();
            task2.setTitle("Finish Spring Boot Project");
            task2.setDescription("Complete the Spring Boot practice project.");
            task2.setDueDate(new Date());

            // Agregar tareas
            taskService.addTask(task1);
            taskService.addTask(task2);

            // Listar todas las tareas
            System.out.println("\nListing all tasks:");
            taskService.listTasks()
                .forEach(t -> System.out.println(t.getTitle() + " - Due: " + t.getDueDate()));

            // Obtener una tarea específica por ID
            Task retrievedTask = taskService.getTaskById(2);
            System.out.println("\nRetrieved Task: " + retrievedTask.getTitle());
        };
    }

}
