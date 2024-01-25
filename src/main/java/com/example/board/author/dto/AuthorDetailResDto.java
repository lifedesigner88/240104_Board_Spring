package com.example.board.author.dto;

import com.example.board.post.dto.PostListResDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDetailResDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdTime;
    private int postSize;
    private List<PostListResDto> posts;
    private String role;
}
