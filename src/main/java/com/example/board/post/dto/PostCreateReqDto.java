package com.example.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PostCreateReqDto {

    private String title;
    private String content;
    private String email;
    private String appointment;
    private String appointmentTime;

}
