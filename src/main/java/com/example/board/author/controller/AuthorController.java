package com.example.board.author.controller;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.AuthorDetailResDto;
import com.example.board.author.dto.AuthorListResDto;
import com.example.board.author.dto.AuthorSaveReqDto;
import com.example.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String authorList(Model model) {
        List<AuthorListResDto> authors = service.findAll();
        model.addAttribute("authors", authors);
        return "author/author-list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model) {
        AuthorDetailResDto author = service.findById(id);
        model.addAttribute("author", author);
        return "author/author-detail";

    }


}
