package io.guatimosim1.controller;

import io.guatimosim1.model.dto.UserRequestDTO;
import io.guatimosim1.model.dto.UserResponseDTO;
import io.guatimosim1.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    @InjectMocks
    private UserController controller;
    @Mock
    private UserService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private static class ObjetosMockados {
        public static final UserRequestDTO USER_REQUEST_DTO = new UserRequestDTO("Naruto", "naruto@email.com", "123");
        public static final UserRequestDTO USER_REQUEST_DTO_UPDATED = new UserRequestDTO("Naruto", "atualizado@email.com", "123");
        public static final UserResponseDTO USER_RESPONSE_DTO = new UserResponseDTO(1L, "Naruto", "naruto@email.com");
        public static final UserResponseDTO USER_RESPONSE_DTO_UPDATED = new UserResponseDTO(1L, "Naruto", "atualizado@email.com");
    }

    @Test
    void findById() {
        Mockito.when(service.findById(1L)).thenReturn(ObjetosMockados.USER_RESPONSE_DTO);

        ResponseEntity<UserResponseDTO> response = controller.findById(1L);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(UserResponseDTO.class, response.getBody().getClass());
        Assertions.assertEquals(ObjetosMockados.USER_RESPONSE_DTO, response.getBody());
    }

    @Test
    void findAll() {
        ArrayList<UserResponseDTO> objects = new ArrayList<>();
        objects.add(ObjetosMockados.USER_RESPONSE_DTO);

        Mockito.when(service.findAll()).thenReturn(objects);

        ResponseEntity<List<UserResponseDTO>> response = controller.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ArrayList.class, response.getBody().getClass());
        Assertions.assertEquals(UserResponseDTO.class, response.getBody().get(0).getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void save() {
        Mockito.when(service.save(ObjetosMockados.USER_REQUEST_DTO)).thenReturn(ObjetosMockados.USER_RESPONSE_DTO);

        ResponseEntity<UserResponseDTO> response = controller.save(ObjetosMockados.USER_REQUEST_DTO);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(UserResponseDTO.class, response.getBody().getClass());
        Assertions.assertEquals(ObjetosMockados.USER_RESPONSE_DTO, response.getBody());
    }

    @Test
    void update() {
        Mockito.when(service.update(1L, ObjetosMockados.USER_REQUEST_DTO_UPDATED)).thenReturn(ObjetosMockados.USER_RESPONSE_DTO_UPDATED);

        ResponseEntity<UserResponseDTO> response = controller.update(1L, ObjetosMockados.USER_REQUEST_DTO_UPDATED);
        UserResponseDTO esperado = ObjetosMockados.USER_RESPONSE_DTO_UPDATED;

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(UserResponseDTO.class, response.getBody().getClass());
        Assertions.assertEquals(esperado, response.getBody());
    }

    @Test
    void delete() {
        Mockito.doNothing().when(service).delete(Mockito.anyLong());

        ResponseEntity<Void> response = controller.delete(1L);

        Assertions.assertNotNull(response);
        Mockito.verify(service, Mockito.times(1)).delete(Mockito.anyLong());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}