package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.exception.ExpenseNotFoundException;
import com.javaupskilling.finalspringproject.model.Expense;
import com.javaupskilling.finalspringproject.service.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final IService<Expense> expenseService;

    @Autowired
    public ExpenseController(IService<Expense> expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAll() {
        return ResponseEntity.ok(expenseService.getAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getById(@PathVariable Long id) {
        try {
            Expense expenseFound = expenseService.getById(id);
            return ResponseEntity.ok(expenseFound);
        } catch (ExpenseNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Expense expense) {
        try {
            expenseService.create(expense);
            return ResponseEntity.ok("Expense created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Expense expense) {
        try {
            expenseService.update(id, expense);
            return ResponseEntity.ok("Expense updated successfully");
        } catch (RuntimeException e) {
            if (e instanceof ExpenseNotFoundException) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            expenseService.delete(id);
            return ResponseEntity.ok("Expense deleted successfully");
        } catch (ExpenseNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
