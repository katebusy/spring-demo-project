package org.example.controllers;

import org.example.model.UserDTO;
import org.example.model.UsersDTO;
import org.example.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringJUnitConfig({
        MainController.class
})
class MainControllerTest {

    @Autowired
    MainController controller;
    @MockBean
    private UserService userService;

    @Test
    void createUser() {
        UUID id1 = UUID.randomUUID();
        UserDTO userDTO = new UserDTO(id1, "TestName1", 12, 2012, null);

        when(userService.createUser(any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = controller.createUser(userDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        UserDTO createdUser = response.getBody();

        assertThat(createdUser).isEqualTo(userDTO);

        verify(userService).createUser(any());
        verifyNoMoreInteractions(userService);
    }


    @Test
    void getAllUsers() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        ArrayList<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO(id1, "TestName1", 12, 2012,  null));
        users.add(new UserDTO(id2, "TestName2", 10, 2014,  null));

        when(userService.getAll()).thenReturn(new UsersDTO(users));

        ResponseEntity<UsersDTO> response = controller.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        UsersDTO responseUsers = response.getBody();
        assertThat(responseUsers.getUsers()).isEqualTo(users);


        verify(userService).getAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    void getUser() {
        UUID id1 = UUID.randomUUID();
        UserDTO userDTO = new UserDTO(id1, "TestName1", 12, 2012, null);

        when(userService.getUser(any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = controller.getUser(id1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        UserDTO answerUser = response.getBody();
        assertThat(answerUser).isEqualTo(userDTO);

        verify(userService).getUser(any());
        verifyNoMoreInteractions(userService);
    }


    @Test
    void updateUser() {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = new UserDTO(id, "TestName1", 12, 2012, null);

        when(userService.updateUser(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = controller.updateUser(id, userDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        UserDTO answerUser = response.getBody();
        assertThat(answerUser).isEqualTo(userDTO);

        verify(userService).updateUser(any(), any());
        verifyNoMoreInteractions(userService);
    }
}