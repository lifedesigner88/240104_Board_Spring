package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.AuthorDetailResDto;
import com.example.board.author.dto.AuthorListResDto;
import com.example.board.author.dto.AuthorSaveReqDto;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository repository;
    public AuthorService(@Autowired AuthorRepository repository) {
        this.repository = repository;
    }

    public Author save(AuthorSaveReqDto req){
        return repository.save(
                new Author(
                        req.getName(),
                        req.getEmail(),
                        req.getPassword()
                ));
    }

    public List<AuthorListResDto> findAll(){
        List<AuthorListResDto> dtoList = new ArrayList<>();
        for(Author res : repository.findAll())
            dtoList.add(
                    new AuthorListResDto(
                            res.getId(),
                            res.getName(),
                            res.getEmail()
                    ));
        return dtoList;
    }

    public AuthorDetailResDto findById(Long id) {
        return makeResDto(
                repository
                        .findById(id)
                        .orElseThrow()
        );
    }

    public AuthorDetailResDto makeResDto (Author a){
        return new AuthorDetailResDto(
                a.getId(),
                a.getName(),
                a.getEmail(),
                a.getPassword(),
                a.getCreatedTime()
        );
    }

}
