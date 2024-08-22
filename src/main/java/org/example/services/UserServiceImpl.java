package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entities.User;
import org.example.mapper.UserMapper;
import org.example.model.UserDTO;
import org.example.model.UsersDTO;
import org.example.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User saved = userRepository.save(user);
        return UserMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UsersDTO getAll() {
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
        return new UsersDTO(users);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUser(UUID id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("There is no user with this id: %s".formatted(id)));
    }

    @Override
    @Transactional
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("There is no user with this id: %s".formatted(id));
        }
        User userEntity = UserMapper.toEntity(userDTO);
        userEntity.setId(id);
        User saved = userRepository.saveAndFlush(userEntity);
        return UserMapper.toDto(saved);
    }
}
