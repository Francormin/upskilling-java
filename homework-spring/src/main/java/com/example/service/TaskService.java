package com.henry.service;

import com.henry.model.Task;

import java.util.List;

public interface TaskService {

    void addTask(Task task);

    Task getTaskById(int taskId);

    List<Task> listTasks();

}
