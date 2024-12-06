package com.javaupskilling.finalspringproject.repository;

import com.javaupskilling.finalspringproject.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
