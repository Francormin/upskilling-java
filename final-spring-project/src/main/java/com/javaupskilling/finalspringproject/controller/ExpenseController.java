package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.dto.ExpenseRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseResponseDto;
import com.javaupskilling.finalspringproject.service.ExpenseService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.javaupskilling.finalspringproject.util.ValidationUtil.validateDate;
import static com.javaupskilling.finalspringproject.util.ValidationUtil.validateId;

@Slf4j
@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getAll() {
        return ResponseEntity.ok(expenseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> getById(@PathVariable Long id) {
        validateId(id);
        return ResponseEntity.ok(expenseService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponseDto>> getByUserId(@PathVariable Long userId) {
        validateId(userId);
        return ResponseEntity.ok(expenseService.getByUserId(userId));
    }

    @GetMapping("/date")
    public ResponseEntity<List<ExpenseResponseDto>> getByDate(@RequestParam String date) {
        validateDate(date);
        return ResponseEntity.ok(expenseService.getByDate(date));
    }

    @GetMapping("/expense-category/{expenseCategoryId}")
    public ResponseEntity<List<ExpenseResponseDto>> getByExpenseCategoryId(
        @PathVariable Long expenseCategoryId) {

        validateId(expenseCategoryId);
        return ResponseEntity.ok(expenseService.getByExpenseCategoryId(expenseCategoryId));

    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDto> create(@RequestBody @Valid ExpenseRequestDto requestDto) {
        log.info("POST - Expense received from body: {}", requestDto);
        ExpenseResponseDto responseDto = expenseService.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> update(
        @PathVariable Long id,
        @RequestBody @Valid ExpenseRequestDto requestDto) {

        validateId(id);
        log.info("PUT - Expense received from body: {}", requestDto);
        ExpenseResponseDto responseDto = expenseService.update(id, requestDto);
        return ResponseEntity.ok(responseDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        validateId(id);
        expenseService.delete(id);
        return ResponseEntity.ok("Expense deleted successfully");
    }

}
