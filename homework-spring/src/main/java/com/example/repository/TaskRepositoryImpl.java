package com.example.repository;

import com.example.model.Task;
import com.example.util.TaskRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {

    private JdbcTemplate jdbcTemplate;

    // Setter method to allow dependency injection from XML
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
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
        return jdbcTemplate.queryForObject(sql, new Object[]{taskId}, new TaskRowMapper());
    }

    @Override
    public List<Task> listTasks() {
        String sql = "SELECT * FROM tasks";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

}
