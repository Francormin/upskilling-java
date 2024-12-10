package com.javaupskilling.finalspringproject.repository;

import com.javaupskilling.finalspringproject.model.ExpenseCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    List<ExpenseCategory> findByNameContainingIgnoreCase(String name);

}
