package com.example.board.post.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 3000, nullable = false)
    private String content;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public Post(){};

    public Post (String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void postUpdate (String title, String content) {
        this.title = title;
        this.content = content;
    }

}
