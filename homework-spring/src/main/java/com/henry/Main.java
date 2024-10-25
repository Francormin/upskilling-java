package com.henry;

import com.henry.config.AppConfig;
import com.henry.model.Task;
import com.henry.service.TaskServiceImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
//        TaskServiceImpl taskService = applicationContext.getBean("taskService", TaskServiceImpl.class);
//        System.out.println(taskService.listTasks());

        // Load the Spring XML configuration file
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        // Get TaskService bean
        TaskServiceImpl taskService = context.getBean("taskService", TaskServiceImpl.class);

        // Create a new task
        Task task = new Task();
        task.setId(1);
        task.setTitle("Finish Spring Project");
        task.setDescription("Complete the Spring framework practice project.");
        task.setDueDate(new Date(System.currentTimeMillis()));

        // Add the task
        taskService.addTask(task);

        // List all tasks
        System.out.println("\nListing all tasks:");
        for (Task t : taskService.listTasks()) {
            System.out.println(t.getTitle() + " - Due: " + t.getDueDate());
        }

        // Retrieve a specific task by id
        Task retrievedTask = taskService.getTaskById(1);
        System.out.println("\nRetrieved Task: " + retrievedTask.getTitle());
    }

}
