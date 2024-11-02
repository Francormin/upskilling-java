package com.example.homeworkspringboot.service;

import com.example.homeworkspringboot.model.Task;

import java.util.List;

public interface TaskService {

    void addTask(Task task);

    Task getTaskById(Integer taskId);

    List<Task> listTasks();

}
