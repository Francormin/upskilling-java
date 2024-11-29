package com.javaupskilling.finalspringproject.repository.impl;

import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.repository.IRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ExpenseRepository implements IRepository<Expense> {

    private final static String SELECT_ALL = "SELECT * FROM expenses";
    private final static String SELECT_BY_ID = "SELECT * FROM expenses WHERE id = ?";
    private final static String DELETE = "DELETE FROM expenses WHERE id = ?";
    private final static String INSERT =
        "INSERT INTO expenses (amount, date, category, description) VALUES (?, ?, ?, ?)";
    private final static String UPDATE =
        "UPDATE expenses SET amount = ?, date = ?, category = ?, description = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpenseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Expense> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Expense.class));
    }

    @Override
    public Optional<Expense> getById(Long id) {
        return jdbcTemplate.query(
                SELECT_BY_ID,
                new BeanPropertyRowMapper<>(Expense.class),
                id)
            .stream()
            .findFirst();
    }

    @Override
    public void create(Expense expense) throws RuntimeException {
        try {
            jdbcTemplate.update(
                INSERT,
                expense.getAmount(),
                expense.getDate(),
                expense.getExpenseCategory(),
                expense.getDescription()
            );
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Expense expense) throws RuntimeException {
        try {
            jdbcTemplate.update(
                UPDATE,
                expense.getAmount(),
                expense.getDate(),
                expense.getExpenseCategory(),
                expense.getDescription(),
                id
            );
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

}
