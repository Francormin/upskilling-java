package com.example.homeworkspringboot.service;

import com.example.homeworkspringboot.model.Task;
import com.example.homeworkspringboot.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void addTask(Task task) {
        taskRepository.addTask(task);
    }

    @Override
    public Task getTaskById(Integer taskId) {
        return taskRepository.getTaskById(taskId);
    }

    @Override
    public List<Task> listTasks() {
        return taskRepository.listTasks();
    }

}
