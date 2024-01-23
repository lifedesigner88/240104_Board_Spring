package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import com.example.board.author.dto.AuthorDetailResDto;
import com.example.board.author.dto.AuthorListResDto;
import com.example.board.author.dto.AuthorSaveReqDto;
import com.example.board.author.dto.AuthorUpdateReqDto;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository repository;
    public AuthorService(@Autowired AuthorRepository repository) {
        this.repository = repository;
    }

    public Author save(AuthorSaveReqDto req){
        Role role;
        String reqRole = req.getRole();
        if (reqRole == null || reqRole.equals("admin")) role = Role.ADMIN;
        else role = Role.USER;
        return repository.save(
                new Author(
                        req.getName(),
                        req.getEmail(),
                        req.getPassword(),
                        role));
    }

    public List<AuthorListResDto> findAll(){
        List<AuthorListResDto> dtoList = new ArrayList<>();
        for(Author res : repository.findAll())
            dtoList.add(
                    new AuthorListResDto(
                            res.getId(),
                            res.getName(),
                            res.getEmail()));
        return dtoList;
    }

    public AuthorDetailResDto findDetailById(Long id) {
        return makeResDto(findById(id));
    }

    public AuthorDetailResDto update (Long id, AuthorUpdateReqDto dto){
        Author author = findById(id);
        author.updateAuthor(
                dto.getName(),
                dto.getPassword());
        return makeResDto(repository.save(author));
    }

    public AuthorDetailResDto makeResDto (Author a){
        Role roleRes = a.getRole();
        String roleString;
        if (roleRes == null || roleRes.equals(Role.ADMIN)) roleString = "관리자";
        else roleString = "유저";
        return new AuthorDetailResDto(
                a.getId(),
                a.getName(),
                a.getEmail(),
                a.getPassword(),
                roleString,
                a.getCreatedTime());
    }

    public Author findById(Long id) throws EntityNotFoundException {
        return repository
                    .findById(id)
                    .orElseThrow(EntityNotFoundException::new);
    }

    public void delete(Long id){repository.deleteById(id);}

}
