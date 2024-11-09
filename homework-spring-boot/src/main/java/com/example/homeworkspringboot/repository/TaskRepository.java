package com.example.homeworkspringboot.repository;

import com.example.homeworkspringboot.model.Task;

import java.util.List;

public interface TaskRepository {

    void addTask(Task task);

    Task getTaskById(int taskId);

    List<Task> listTasks();

}
