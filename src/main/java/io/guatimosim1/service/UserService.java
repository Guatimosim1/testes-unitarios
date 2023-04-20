package io.guatimosim1.service;

import io.guatimosim1.exception.classes.IntegridadeDadosException;
import io.guatimosim1.exception.classes.UserNotFoundException;
import io.guatimosim1.model.dto.UserRequestDTO;
import io.guatimosim1.model.dto.UserResponseDTO;
import io.guatimosim1.model.entity.User;
import io.guatimosim1.model.factory.UserFactory;
import io.guatimosim1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public UserResponseDTO findById(Long id) {
        return UserFactory.toResponseDTO(repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Não foi encontrado usuário com id "+id)
        ));
    }

    public List<UserResponseDTO> findAll() {
        return repository.findAll().stream().map(UserFactory::toResponseDTO).collect(Collectors.toList());
    }

    public UserResponseDTO save(UserRequestDTO request) {
        if(repository.findByEmail(request.getEmail()).isPresent()) throw new IntegridadeDadosException("O email informado já está cadastrado no sistema");
        return UserFactory.toResponseDTO(repository.save(UserFactory.toEntity(request)));
    }

    public UserResponseDTO update(Long id, UserRequestDTO request) {
        if(repository.findByEmail(request.getEmail()).isPresent()) throw new IntegridadeDadosException("O email informado já está cadastrado no sistema");

        User user = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Não foi encontrado usuário com id " + id)
        );

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return UserFactory.toResponseDTO(repository.save(user));
    }

    public void delete(Long id) {
        User user = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Não foi encontrado usuário com id " + id)
        );
        repository.delete(user);
    }
}
