package com.example.demo.entity;

import com.example.demo.dto.user.UserCreateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 100, name = "email")
    private String username;
    @Setter
    @Column(nullable = false, length = 200)
    private String password;
    @Setter
    @Column(nullable = false, length = 50)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENT;

    public User(UserCreateDTO dto) {
        this.username = dto.username();
        this.password = dto.password();
        this.name = dto.name().toUpperCase();
    }

    public enum Role {
        ROLE_ADMIN, ROLE_CLIENT
    }
}
