package com.example;

import com.example.model.Task;
import com.example.service.TaskService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class SpringApplication {

    public static void main(String[] args) {
        // Load the Spring XML configuration file
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        // Get TaskService bean
        TaskService taskService = context.getBean("taskService", TaskService.class);

        // Create new tasks
        Task task1 = new Task();
        task1.setId(1);
        task1.setTitle("Start Spring Project");
        task1.setDescription("Initialize the Spring practice project.");
        task1.setDueDate(new Date(System.currentTimeMillis()));

        Task task2 = new Task();
        task2.setId(2);
        task2.setTitle("Finish Spring Project");
        task2.setDescription("Complete the Spring practice project.");
        task2.setDueDate(new Date(System.currentTimeMillis()));

        // Add the tasks
        taskService.addTask(task1);
        taskService.addTask(task2);

        // List all tasks
        System.out.println("\nListing all tasks:");
        taskService.listTasks().forEach(t -> System.out.println(t.getTitle() + " - Due: " + t.getDueDate()));

        // Retrieve a specific task by id
        Task retrievedTask = taskService.getTaskById(2);
        System.out.println("\nRetrieved Task: " + retrievedTask.getTitle());
    }

}
