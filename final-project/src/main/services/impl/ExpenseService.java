package services.impl;

import dao.DAO;
import entities.Expense;
import services.Service;

import java.util.List;

public class ExpenseService implements Service<Expense> {
    private final DAO<Expense> dao;

    public ExpenseService(DAO<Expense> dao) {
        this.dao = dao;
    }

    @Override
    public List<Expense> getAll() {
        return dao.getAll();
    }

    @Override
    public Expense getById(int id) {
        return dao.getById(id);
    }

    @Override
    public void add(Expense expense) {
        dao.add(expense);
    }

    @Override
    public void update(int id, Expense expense) {
        dao.update(id, expense);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}