package com.example.board.author.controller;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.AuthorDetailResDto;
import com.example.board.author.dto.AuthorListResDto;
import com.example.board.author.dto.AuthorSaveReqDto;
import com.example.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService service;

    public AuthorController(@Autowired AuthorService service) {
        this.service = service;
    }

    @PostMapping("/author/save")
    @ResponseBody
    public Author authorSave(AuthorSaveReqDto dto) {
        return service.save(dto);
    }

    @GetMapping("/author/list")
    @ResponseBody
    public List<AuthorListResDto> authorList() {
        return service.findAll();
    }

    @ResponseBody
    @GetMapping("/author/detail/{id}")
    public AuthorDetailResDto authorDetail(@PathVariable Long id) {
        System.out.println(id);
        return service.findById(id);
    }
}
