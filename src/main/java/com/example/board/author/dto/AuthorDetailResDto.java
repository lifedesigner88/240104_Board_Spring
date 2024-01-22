package com.example.board.author.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuthorDetailResDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdTime;
}
