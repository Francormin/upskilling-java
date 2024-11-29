package com.javaupskilling.finalspringproject.service.impl;

import com.javaupskilling.finalspringproject.exception.ExpenseNotFoundException;
import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.repository.IRepository;
import com.javaupskilling.finalspringproject.service.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService implements IService<Expense> {

    private final IRepository<Expense> expenseRepository;

    @Autowired
    public ExpenseService(IRepository<Expense> expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getAll() {
        return expenseRepository.getAll();
    }

    @Override
    public Expense getById(Long id) throws ExpenseNotFoundException {
        Optional<Expense> expenseFound = expenseRepository.getById(id);
        if (expenseFound.isEmpty()) {
            throw new ExpenseNotFoundException("Expense not found");
        }
        return expenseFound.get();
    }

    @Override
    public void create(Expense expense) throws RuntimeException {
        try {
            expenseRepository.create(expense);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Expense expense) throws RuntimeException {
        try {
            getById(id);
            expenseRepository.update(id, expense);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws ExpenseNotFoundException {
        try {
            getById(id);
            expenseRepository.delete(id);
        } catch (ExpenseNotFoundException e) {
            throw new ExpenseNotFoundException(e.getMessage());
        }
    }

}
