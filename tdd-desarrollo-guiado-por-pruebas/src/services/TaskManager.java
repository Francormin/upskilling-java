package services;

import entities.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskManager {

    private final List<Task> tasks = new ArrayList<>();

    // ToDo -> refactorizar con persistencia y guardado en base de datos
    //          + implementar operaciones CRUD en una clase DAO

    public Task addTask(String title) {
        Task task = new Task(UUID.randomUUID(), title, false);
        tasks.add(task);
        return task;
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public void completeTask(Task task) {
        task.setCompleted(true);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(UUID taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

}
