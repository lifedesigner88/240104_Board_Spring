package com.example.board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorListResDto {
    private Long id;
    private String name;
    private String email;
    private int postSize;
}
