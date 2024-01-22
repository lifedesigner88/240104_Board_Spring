package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.AuthorListResDto;
import com.example.board.author.dto.AuthorSaveReqDto;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository Repository;
    public AuthorService(@Autowired AuthorRepository Repository) {
        this.Repository = Repository;
    }

    public Author save(AuthorSaveReqDto dto){
        return Repository.save(
                new Author(
                        dto.getName(),
                        dto.getEmail(),
                        dto.getPassword()
                ));
    }

    public List<AuthorListResDto> findAll(){
        List<AuthorListResDto> dtoList = new ArrayList<>();
        for(Author a : Repository.findAll())
            dtoList.add(
                    new AuthorListResDto(
                            a.getId(),
                            a.getName(),
                            a.getEmail()
                    ));
        return dtoList;
    }

}
