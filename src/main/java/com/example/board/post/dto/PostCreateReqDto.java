package com.example.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateReqDto {

    private String title;
    private String content;
    private String email;

}
