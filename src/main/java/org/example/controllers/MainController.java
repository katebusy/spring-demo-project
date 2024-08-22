package org.example.controllers;

import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.example.model.UserDTO;
import org.example.model.UsersDTO;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController("/")
public class MainController {

    @Autowired
    private UserService userService;

    /**
     * Эндпоинт, который возвращает всех существующих в базе пользователей
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<UsersDTO> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    /**
     * Эндпоинт, который принимает в качестве аргумента id пользователя и возвращает UserDTO
     *
     * @param id
     * @return {@link UserDTO}
     */
    @GetMapping("/userDTO")
    public ResponseEntity<UserDTO> getUser(UUID id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    /**
     * Эндпоинт, который принимает в качестве аргумента пользователя, добавляет его в базу данных и возвращает UserDTO
     *
     * @param user
     * @return {@link UserDTO}
     */
    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    /**
     * Эндпоинт, который принимает в качестве аргумента id пользователя и UserDTO, обновляет его данные в базе данных,
     * если пользователь существует и возвращает UserDTO.
     *
     * @param id
     * @param user {@link UserDTO}
     * @return {@link UserDTO}
     */
    @PutMapping("/user")
    public ResponseEntity<UserDTO> updateUser(@PathParam("id") UUID id, @RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }


}
