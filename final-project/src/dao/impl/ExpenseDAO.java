package dao.impl;

import dao.DAO;
import entities.Expense;

import java.util.List;

public class ExpenseDAO implements DAO<Expense> {
    @Override
    public List<Expense> getAll() {
        return List.of();
    }

    @Override
    public Expense getById(int id) {
        return null;
    }

    @Override
    public void add(Expense expense) {

    }

    @Override
    public void update(int id, Expense expense) {

    }

    @Override
    public void delete(int id) {

    }
}
