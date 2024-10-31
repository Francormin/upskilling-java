package com.example.service;

import com.example.model.Task;

import java.util.List;

public interface TaskService {

    void addTask(Task task);

    Task getTaskById(int taskId);

    List<Task> listTasks();

}
