package org.example.services;

import org.example.entities.User;
import org.example.model.UserDTO;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig({
        UserServiceImpl.class
})
@MockBean(UserRepository.class)
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser() {
        when(userRepository.save(any())).thenAnswer(inv -> {
            User user = inv.getArgument(0, User.class);
            user.setId(UUID.randomUUID());
            user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return user;
        });

        UserDTO user = new UserDTO(null, "TestName", 12, 2012, null);
        UserDTO userSaved = userService.createUser(user);
        assertThat(userSaved).isNotNull();
        assertThat(userSaved.getUpdatedAt()).isNotNull();
        assertThat(userSaved.getId()).isNotNull();

        verify(userRepository).save(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getAll() {
        when(userRepository.findAll()).thenAnswer(invocation -> {
           List<User> users = new ArrayList<>();
            UUID id1 = UUID.randomUUID();
            UUID id2 = UUID.randomUUID();
           users.add(new User(id1, "TestName1", 12,  null));
            users.add(new User(id2, "TestName2", 12,  null));
           return users;
        });

        List<UserDTO> all = userService.getAll().getUsers();
        assertThat(all)
                .isNotNull()
                .isNotEmpty();
        for (UserDTO userDTO : all) {
            assertThat(userDTO).isNotNull();
            assertThat(userDTO.getId()).isNotNull();
        }
        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getUser() {
        UUID id = UUID.fromString("276f06dd-b47e-4b88-b9e7-88d7714ee8a4");
        User user = new User(id, "TestName1", 12, null);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.getUser(id);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getId()).isEqualTo(user.getId());
        assertThat(userDTO.getName()).isEqualTo(user.getName());
        assertThat(userDTO.getAge()).isEqualTo(user.getAge());
        assertThat(userDTO.getBirthYear()).isEqualTo(LocalDateTime.now().getYear() - user.getAge());


        verify(userRepository).findById(any());
        verifyNoMoreInteractions(userRepository);

    }

    @Test
    void getUnexcitingUser() {
        UUID id = UUID.fromString("276f06dd-b47e-4b88-b9e7-88d7714ee8a4");

        when(userRepository.findById(any())).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            userService.getUser(id);
        });

        String expectedMessage = "There is no user with this id: 276f06dd-b47e-4b88-b9e7-88d7714ee8a4";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateUser() {
        when(userRepository.saveAndFlush(any())).thenAnswer(inv -> {
            User user = inv.getArgument(0, User.class);
            user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return user;
        });
        when(userRepository.existsById(any())).thenReturn(true);

        UUID id = UUID.fromString("276f06dd-b47e-4b88-b9e7-88d7714ee8a4");
        UserDTO userDTO1 = new UserDTO(id, "TestName", 12, 2012, null);

        UserDTO updatedUser = userService.updateUser(id, userDTO1);
        UserDTO expected = new UserDTO(id, "TestName", 12, 2012,
                Timestamp.valueOf(LocalDateTime.now()));

        assertEquals(expected, updatedUser);
        assertThat(updatedUser.getUpdatedAt()).isNotNull();

        verify(userRepository).existsById(any());
        verify(userRepository).saveAndFlush(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUnexcitingUser() {
        when(userRepository.existsById(any())).thenReturn(false);

        UUID id = UUID.fromString("276f06dd-b47e-4b88-b9e7-88d7714ee8a4");
        UserDTO userDTO = new UserDTO(id, "TestName", 12, 2012, null);


        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            userService.updateUser(id, userDTO);
        });

        String expectedMessage = "There is no user with this id: 276f06dd-b47e-4b88-b9e7-88d7714ee8a4";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}