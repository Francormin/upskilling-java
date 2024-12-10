package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.dto.ExpenseCategoryRequestDto;
import com.javaupskilling.finalspringproject.dto.ExpenseCategoryResponseDto;
import com.javaupskilling.finalspringproject.dto.ExpenseResponseDto;
import com.javaupskilling.finalspringproject.dto.UserRequestDto;
import com.javaupskilling.finalspringproject.dto.UserResponseDto;
import com.javaupskilling.finalspringproject.model.ExpenseCategory;
import com.javaupskilling.finalspringproject.service.ExpenseCategoryService;

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

import static com.javaupskilling.finalspringproject.util.ValidationUtil.validateId;

@Slf4j
@RestController
@RequestMapping("/api/v1/expense-categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategoryResponseDto>> getAll() {
        return ResponseEntity.ok(expenseCategoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseCategoryResponseDto> getById(@PathVariable Long id) {
        validateId(id);
        return ResponseEntity.ok(expenseCategoryService.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ExpenseCategoryResponseDto>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(expenseCategoryService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<ExpenseCategoryResponseDto> create(
        @RequestBody @Valid ExpenseCategoryRequestDto requestDto) {

        log.info("POST - ExpenseCategory received from body: {}", requestDto);
        ExpenseCategoryResponseDto responseDto = expenseCategoryService.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseCategoryResponseDto> update(
        @PathVariable Long id,
        @RequestBody @Valid ExpenseCategoryRequestDto requestDto) {

        validateId(id);
        log.info("PUT - ExpenseCategory received from body: {}", requestDto);
        ExpenseCategoryResponseDto responseDto = expenseCategoryService.update(id, requestDto);
        return ResponseEntity.ok(responseDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        validateId(id);
        expenseCategoryService.delete(id);
        return ResponseEntity.ok("ExpenseCategory deleted successfully");
    }

}
