package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;


@Entity
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    @EqualsAndHashCode.Exclude
    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private Timestamp updatedAt;

}
