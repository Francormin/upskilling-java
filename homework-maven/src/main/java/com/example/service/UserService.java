package com.example.service;

import com.example.dto.UserDTO;
import com.example.exception.ResourceNotFoundCustomException;
import com.example.model.User;
import com.example.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    // No es necesario @Autowired, ya que la clase posee un solo constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Convierte un User en un UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setAge(user.getAge());
        return userDTO;
    }

    // Convierte un UserDTO en un User
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());
        return user;
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
            .map(this::convertToDTO)
            .toList();
    }

    public UserDTO getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundCustomException("user not found")
        );
        return convertToDTO(user);
    }

    public UserDTO create(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public void update(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundCustomException("user not found")
        );
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());
        userRepository.save(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundCustomException("user not found")
        );
        userRepository.delete(user);
    }

}
