package com.example.board.author.domain;

import com.example.board.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

//    mappedBy에 연관계의 주인을 명시하고, fk를 관리하는 변수명을 명시
//    @Setter   // cascade.persist를 위한 테스트
//    @OnetoOne  1:1 관계인 경우
    @Setter
    @OneToMany(mappedBy ="author", cascade = CascadeType.ALL)
    private List<Post> posts;

//    Time
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

    public void authorUpdate(String name, String password) {
        this.name = name;
        this.password = password;
    }

}

