package com.example.repository;

import com.example.model.Task;

import java.util.List;

public interface TaskRepository {

    void addTask(Task task);

    Task getTaskById(int taskId);

    List<Task> listTasks();

}
