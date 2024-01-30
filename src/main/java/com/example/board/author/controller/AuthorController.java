package com.example.board.author.controller;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.AuthorDetailResDto;
import com.example.board.author.dto.AuthorListResDto;
import com.example.board.author.dto.AuthorSaveReqDto;
import com.example.board.author.dto.AuthorUpdateReqDto;
import com.example.board.author.service.AuthorService;
import com.example.board.post.dto.PostListResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("author")
public class AuthorController {

    private final AuthorService service;
    public AuthorController(@Autowired AuthorService service) {
        this.service = service;
    }


//    Login
    @GetMapping("login-page")
    public String authorLogin() {
        return "author/author-login";
    }


//    Create
    @GetMapping("create")
    public String authorCreate(){
        return "author/author-create";
    }

    @PostMapping("create")
    public String authorSave(AuthorSaveReqDto dto, Model model) {
        try {
            Author author = service.save(dto);
            return "redirect:/author/detail/" + author.getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            log.error(e.getMessage());
            return "author/author-create";
        }
    }

//    Read
    @GetMapping("list")
    public String getAllAuthors(Model model) {
        List<AuthorListResDto> authors = service.findAll();
        model.addAttribute("authors", authors);
        return "author/author-list";
    }

    @GetMapping("detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model) throws EntityNotFoundException {
        AuthorDetailResDto author = service.findDetailById(id);
        List<PostListResDto> list = author.getPosts();
        model.addAttribute("author", author);
        model.addAttribute("posts", list);
        return "author/author-detail";
    }

//    Update
    @PostMapping("update/{id}")
    public String updateAuthor(@PathVariable Long id, AuthorUpdateReqDto dto) {
        AuthorDetailResDto resDto = service.update(id, dto);
        return "redirect:/author/detail/" + resDto.getId();
    }

//    Delete
    @GetMapping("delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/author/list";
    }


//    순환참조 이슈 테스트
    @GetMapping("{id}/circle/entity")
    @ResponseBody
    public Author circleIssueTest1(@PathVariable Long id){
        return service.findById(id);
    }

    @GetMapping("{id}/circle/dto")
    @ResponseBody
    public AuthorDetailResDto circleIssueTest2(@PathVariable Long id){
        return service.findDetailById(id);
    }
}
