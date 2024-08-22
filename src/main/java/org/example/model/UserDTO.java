package org.example.model;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class UserDTO {

    private UUID id;
    private String name;
    private int age;
    private int birthYear;
    @EqualsAndHashCode.Exclude
    private Timestamp updatedAt;
}
