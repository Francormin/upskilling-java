package com.javaupskilling.finalspringproject.controller;

import com.javaupskilling.finalspringproject.dto.UserRequestDto;
import com.javaupskilling.finalspringproject.dto.UserResponseDto;
import com.javaupskilling.finalspringproject.service.UserService;

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
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        validateId(id);
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto requestDto) {
        log.info("POST - User received from body: {}", requestDto);
        UserResponseDto responseDto = userService.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
        @PathVariable Long id,
        @RequestBody @Valid UserRequestDto requestDto) {

        validateId(id);
        log.info("PUT - User received from body: {}", requestDto);
        UserResponseDto responseDto = userService.update(id, requestDto);
        return ResponseEntity.ok(responseDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        validateId(id);
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
