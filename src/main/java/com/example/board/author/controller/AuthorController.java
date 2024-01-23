package com.example.board.author.controller;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.AuthorDetailResDto;
import com.example.board.author.dto.AuthorListResDto;
import com.example.board.author.dto.AuthorSaveReqDto;
import com.example.board.author.dto.AuthorUpdateReqDto;
import com.example.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService service;

    public AuthorController(@Autowired AuthorService service) {
        this.service = service;
    }

    @GetMapping("/author/create")
    public String authorCreate(){
        return "author/author-create";
    }

    @PostMapping("/author/create")
    public String authorSave(AuthorSaveReqDto dto) {
        Author author = service.save(dto);
        return "redirect:/author/detail/" + author.getId();
    }

    @GetMapping("/author/list")
    public String authorList(Model model) {
        List<AuthorListResDto> authors = service.findAll();
        model.addAttribute("authors", authors);
        return "author/author-list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model) {
        AuthorDetailResDto author = service.findDetailById(id);
        model.addAttribute("author", author);
        return "author/author-detail";
    }

    @PostMapping("/author/update/{id}")
    public String updateAuthor(@PathVariable Long id, AuthorUpdateReqDto dto) {
        AuthorDetailResDto resDto = service.update(id, dto);
        return "redirect:/author/detail/" + resDto.getId();
    }

    @GetMapping("/author/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/author/list";
    }

}
