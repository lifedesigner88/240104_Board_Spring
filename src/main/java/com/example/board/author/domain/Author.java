package com.example.board.author.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class Author {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 20, nullable = false)
    private String name;

    @Column(unique = true, length = 20, nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Setter
    @CreationTimestamp
    private LocalDateTime createdTime;

    @Setter
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public Author() {}

    @Builder
    public Author(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void updateAuthor(String name, String password) {
        this.name = name;
        this.password = password;
    }

}

