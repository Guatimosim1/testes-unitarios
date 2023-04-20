package io.guatimosim1.controller;

import io.guatimosim1.model.dto.UserRequestDTO;
import io.guatimosim1.model.dto.UserResponseDTO;
import io.guatimosim1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService service;
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@RequestBody UserRequestDTO request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable("id") Long id, @RequestBody UserRequestDTO request) {
        return new ResponseEntity<>(service.update(id, request), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
