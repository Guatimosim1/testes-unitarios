package io.guatimosim1.service;

import io.guatimosim1.exception.classes.IntegridadeDadosException;
import io.guatimosim1.exception.classes.UserNotFoundException;
import io.guatimosim1.model.dto.UserRequestDTO;
import io.guatimosim1.model.dto.UserResponseDTO;
import io.guatimosim1.model.entity.User;
import io.guatimosim1.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;

    private final ObjetosMockados objetosMockados = new ObjetosMockados();

    private class ObjetosMockados {
        public final User USER = new User(1L, "Naruto", "naruto@email.com", "123");
        public final User USER_TO_BE_SAVED = new User(null, "Naruto", "naruto@email.com", "123");
        public final User USER_TO_BE_UPDATED = new User(1L, "Naruto", "atualizar@email.com", "123");
        public final UserRequestDTO USER_REQUEST_DTO = new UserRequestDTO("Naruto", "naruto@email.com", "123");
        public final UserRequestDTO USER_REQUEST_DTO_UPDATED = new UserRequestDTO("Naruto", "atualizar@email.com", "123");
        public final UserResponseDTO USER_RESPONSE_DTO = new UserResponseDTO(1L, "Naruto", "naruto@email.com");
        public final UserResponseDTO USER_RESPONSE_DTO_UPDATED = new UserResponseDTO(1L, "Naruto", "atualizar@email.com");
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(objetosMockados.USER));

        UserResponseDTO response = service.findById(1L);
        UserResponseDTO esperado = objetosMockados.USER_RESPONSE_DTO;

        Assertions.assertNotNull(response);
        Assertions.assertEquals(UserResponseDTO.class, esperado.getClass());
        Assertions.assertEquals(esperado, response);
    }

    @Test
    void findById_ThrowsUserNotFoundException() {
        Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());

        try {
            service.findById(2L);
            Assertions.fail();
        } catch (Exception exception) {
            Assertions.assertEquals(UserNotFoundException.class, exception.getClass());
            Assertions.assertEquals("Não foi encontrado usuário com id 2", exception.getMessage());
        }
    }

    @Test
    void findAll() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(objetosMockados.USER));

        List<UserResponseDTO> response = service.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(UserResponseDTO.class, response.get(0).getClass());
        Assertions.assertEquals(objetosMockados.USER_RESPONSE_DTO, response.get(0));
    }

    @Test
    void save() {
        Mockito.when(repository.save(objetosMockados.USER_TO_BE_SAVED)).thenReturn(objetosMockados.USER);

        UserResponseDTO response = service.save(objetosMockados.USER_REQUEST_DTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(UserResponseDTO.class, response.getClass());
        Assertions.assertEquals(objetosMockados.USER_RESPONSE_DTO, response);
    }

    @Test
    void save_throwsIntegridadeDadosException() {
        Mockito.when(repository.findByEmail("naruto@email.com")).thenReturn(Optional.of(objetosMockados.USER));
        try {
            service.save(objetosMockados.USER_REQUEST_DTO);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IntegridadeDadosException.class, e.getClass());
            Assertions.assertEquals("O email informado já está cadastrado no sistema", e.getMessage());
        }
    }

    @Test
    void update() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(objetosMockados.USER));
        Mockito.when(repository.findByEmail("atualizar@email.com")).thenReturn(Optional.empty());
        Mockito.when(repository.save(objetosMockados.USER_TO_BE_UPDATED)).thenReturn(objetosMockados.USER_TO_BE_UPDATED);

        UserResponseDTO response = service.update(1L, objetosMockados.USER_REQUEST_DTO_UPDATED);
        UserResponseDTO esperado = objetosMockados.USER_RESPONSE_DTO_UPDATED;

        Assertions.assertNotNull(response);
        Assertions.assertEquals(UserResponseDTO.class, response.getClass());
        Assertions.assertEquals(esperado, response);
    }

    @Test
    void update_throwsUserNotFoundException() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        try {
            service.update(1L, objetosMockados.USER_REQUEST_DTO_UPDATED);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(UserNotFoundException.class, e.getClass());
            Assertions.assertEquals("Não foi encontrado usuário com id 1", e.getMessage());
        }
    }

    @Test
    void update_throwsIntegridadeDadosException() {
        User retorno = objetosMockados.USER_TO_BE_UPDATED;
        retorno.setId(2L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(objetosMockados.USER));
        Mockito.when(repository.findByEmail("atualizar@email.com")).thenReturn(Optional.of(retorno));

        try {
            service.update(1L, objetosMockados.USER_REQUEST_DTO_UPDATED);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(IntegridadeDadosException.class, e.getClass());
            Assertions.assertEquals("O email informado já está cadastrado no sistema", e.getMessage());
        }
    }

    @Test
    void delete() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(objetosMockados.USER));
        Mockito.doNothing().when(repository).delete(objetosMockados.USER);

        service.delete(1L);

        Mockito.verify(repository, Mockito.times(1)).delete(objetosMockados.USER);
    }

    @Test
    void delete_throwsUserNotFoundException() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        try {
            service.delete(1L);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals(UserNotFoundException.class, e.getClass());
            Assertions.assertEquals("Não foi encontrado usuário com id 1", e.getMessage());
        }
    }
}