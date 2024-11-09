package com.example.homeworkspringboot;

import com.example.homeworkspringboot.model.Task;
import com.example.homeworkspringboot.service.TaskService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;

@SpringBootApplication
public class SpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }

    @Bean
    CommandLineRunner run(TaskService taskService, DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("schema.sql"));
            }

            Task task1 = new Task();
            task1.setId(1);
            task1.setTitle("Start Spring Boot Project");
            task1.setDescription("Initialize the Spring Boot practice project.");
            task1.setDueDate(new Date());

            Task task2 = new Task();
            task2.setId(2);
            task2.setTitle("Finish Spring Boot Project");
            task2.setDescription("Complete the Spring Boot practice project.");
            task2.setDueDate(new Date());

            taskService.addTask(task1);
            taskService.addTask(task2);

            System.out.println("\nListing all tasks:");
            taskService.listTasks()
                .forEach(t -> System.out.println(t.getTitle() + " - Due: " + t.getDueDate()));

            Task retrievedTask = taskService.getTaskById(2);
            System.out.println("\nRetrieved Task: " + retrievedTask.getTitle());
        };
    }

}
