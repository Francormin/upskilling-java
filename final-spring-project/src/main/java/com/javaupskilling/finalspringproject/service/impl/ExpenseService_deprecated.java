package com.javaupskilling.finalspringproject.service.impl;

import com.javaupskilling.finalspringproject.exception.ExpenseNotFoundException_deprecated;
import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.repository.IRepository_deprecated;
import com.javaupskilling.finalspringproject.service.IService_deprecated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Deprecated
@Service
public class ExpenseService_deprecated implements IService_deprecated<Expense> {

    private final IRepository_deprecated<Expense> expenseRepository;

    @Autowired
    public ExpenseService_deprecated(IRepository_deprecated<Expense> expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getAll() {
        return expenseRepository.getAll();
    }

    @Override
    public Expense getById(Long id) throws ExpenseNotFoundException_deprecated {
        Optional<Expense> expenseFound = expenseRepository.getById(id);
        if (expenseFound.isEmpty()) {
            throw new ExpenseNotFoundException_deprecated("Expense not found");
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
    public void delete(Long id) throws ExpenseNotFoundException_deprecated {
        try {
            getById(id);
            expenseRepository.delete(id);
        } catch (ExpenseNotFoundException_deprecated e) {
            throw new ExpenseNotFoundException_deprecated(e.getMessage());
        }
    }

}
