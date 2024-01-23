package com.example.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateReqDto {

    private Long id;
    private String title;
    private String content;

}
