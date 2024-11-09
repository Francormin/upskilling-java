package com.example.homeworkspringboot.repository;

import com.example.homeworkspringboot.model.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addTask(Task task) {
        String sql = "INSERT INTO tasks (id, title, description, due_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, task.getId(), task.getTitle(), task.getDescription(), task.getDueDate());
        System.out.println("Task added: " + task.getTitle());
    }

    @Override
    public Task getTaskById(int taskId) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{taskId}, new BeanPropertyRowMapper<>(Task.class));
    }

    @Override
    public List<Task> listTasks() {
        String sql = "SELECT * FROM tasks";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Task.class));
    }

}
