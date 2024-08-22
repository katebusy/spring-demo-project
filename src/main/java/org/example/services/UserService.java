package org.example.services;

import org.example.model.UserDTO;
import org.example.model.UsersDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UsersDTO getAll();
    UserDTO getUser(UUID id);
    UserDTO updateUser(UUID uuid, UserDTO userDTO);
}
