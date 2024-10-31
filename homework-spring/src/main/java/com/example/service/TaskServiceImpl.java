package com.henry.service;

import com.henry.model.Task;
import com.henry.util.TaskRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class TaskServiceImpl implements TaskService {

//    // A simple in-memory list to store tasks for this example
//    private final List<Task> tasks = new ArrayList<>();

    private JdbcTemplate jdbcTemplate;

    // Setter for JdbcTemplate (Spring will inject this automatically)
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addTask(Task task) {
//        tasks.add(task);
//        System.out.println("Task added: " + task.getTitle());

        String sql = "INSERT INTO tasks (id, title, description, due_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, task.getId(), task.getTitle(), task.getDescription(), task.getDueDate());
        System.out.println("Task added to database: " + task.getTitle());
    }

    @Override
    public Task getTaskById(int taskId) {
//        return tasks.stream()
//            .filter(task -> task.getId() == taskId)
//            .findFirst()
//            .orElse(null);

        String sql = "SELECT * FROM tasks WHERE id = ?";
        return (Task) jdbcTemplate.queryForObject(sql, new Object[]{taskId}, new TaskRowMapper());
    }

    @Override
    public List<Task> listTasks() {
//        return tasks;

        String sql = "SELECT * FROM tasks";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

}
