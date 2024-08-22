package org.example.mapper;
import jakarta.inject.Inject;
import org.example.entities.User;
import org.example.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

class UserMapperTest {

    @Test
    void testToEntity() {
        UUID id = UUID.randomUUID();
        Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());

        UserDTO dto = new UserDTO(id, "TestName", 1, 1999, updatedAt);
        User expected = new User(id, "TestName", 1, updatedAt);

        User actual = UserMapper.toEntity(dto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toDto() {
        UUID id = UUID.randomUUID();
        Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());

        User entity = new User(id, "TestName", 111, updatedAt);
        UserDTO expected = new UserDTO(id, "TestName", 111, 1913, updatedAt);

        UserDTO actual = UserMapper.toDto(entity);
        Assertions.assertEquals(expected, actual);
    }
}