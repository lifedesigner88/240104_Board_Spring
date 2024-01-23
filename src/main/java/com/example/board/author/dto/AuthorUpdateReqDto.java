package com.example.board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorUpdateReqDto {

    private String name;
    private String password;

}
