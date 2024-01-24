package com.example.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListResDto {

    private Long id;
    private String title;
    private String author_email;

}