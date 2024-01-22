package com.example.board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorSaveReqDto {
    private String name;
    private String email;
    private String password;
}
