package com.example.demo.entity;

import com.example.demo.dto.user.UserCreateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENT;

    public User(UserCreateDTO dto) {
        this.username = dto.username();
        this.password = dto.password();
    }

    public enum Role {
        ROLE_ADMIN, ROLE_CLIENT
    }
}
