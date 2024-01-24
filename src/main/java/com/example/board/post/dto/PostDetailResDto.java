package com.example.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostDetailResDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdTime;
    private String authorEmail;
    private String authorName;


}
