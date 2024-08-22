package org.example.mapper;

import org.example.entities.User;
import org.example.model.UserDTO;

import java.time.LocalDate;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static UserDTO toDto(User entity) {
        if (entity == null) {
            return null;
        }

        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .updatedAt(entity.getUpdatedAt())
                .birthYear(toBirthYear(entity.getAge()))
                .build();
    }

    private static int toBirthYear(int age) {
        return LocalDate.now().getYear() - age;
    }
}
