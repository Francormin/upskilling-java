package com.example.service;

import com.example.model.Task;
import com.example.repository.TaskRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    // Setter method to allow dependency injection from XML
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public void addTask(Task task) {
        taskRepository.addTask(task);
    }

    @Override
    public Task getTaskById(int taskId) {
        return taskRepository.getTaskById(taskId);
    }

    @Override
    public List<Task> listTasks() {
        return taskRepository.listTasks();
    }

}
