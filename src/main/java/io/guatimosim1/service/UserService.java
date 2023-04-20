package io.guatimosim1.service;

import io.guatimosim1.exception.classes.UserNotFoundException;
import io.guatimosim1.model.dto.UserResponseDTO;
import io.guatimosim1.model.factory.UserFactory;
import io.guatimosim1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public UserResponseDTO findById(Long id) {
        return UserFactory.toResponseDTO(repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Não foi encontrado usuário com id "+id)
        ));
    }
}
