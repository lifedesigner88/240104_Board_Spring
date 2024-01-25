package com.example.board.author.controller;


import com.example.board.author.dto.AuthorDetailResDto;
import com.example.board.author.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;


// WebMvcTest를 이용해서 Controller 계층을 테스트, 모든 스프링 빈을 생성하고 주입하지는 않음.
@WebMvcTest(AuthorController.class)

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
//    @WithMockUser // security 의존성 추가 필요
    void authorDtoTest() throws Exception {

        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();

        authorDetailResDto.setName("dadf");
        authorDetailResDto.setEmail("dafd");
        authorDetailResDto.setPassword("asdf");
//        Mockito.when(authorService.findById(1L)).thenReturn(authorDetailResDto);

        mockMvc.perform((MockMvcRequestBuilders.get("/author/1/circle/dto")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", authorDetailResDto.getName()));

    }



}
