package com.example.board.author.controller;

import com.example.board.author.dto.AuthorListResDto;
import com.example.board.author.dto.AuthorSaveReqDto;
import com.example.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService Service;
    public AuthorController(@Autowired AuthorService Service) {
        this.Service = Service;
    }

    @PostMapping("/author/save")
    @ResponseBody
    public String authorSave(AuthorSaveReqDto dto){
        Service.save(dto);
        return "OK";
    }

    @GetMapping("/author/list")
    @ResponseBody
    public List<AuthorListResDto> authorList(){
        return Service.findAll();
    }

}
