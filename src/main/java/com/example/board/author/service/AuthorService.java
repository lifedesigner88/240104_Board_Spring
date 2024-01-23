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

//    Create
    public Author save(AuthorSaveReqDto req){
        Role role;
        String reqRole = req.getRole();
        if (reqRole == null || reqRole.equals("admin")) role = Role.ADMIN;
        else role = Role.USER;

        Author author = Author.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(req.getPassword())
                .build();

//      Author author = new Author(
//                        req.getName(),
//                        req.getEmail(),
//                        req.getPassword(),
//                        role));

        return repository.save(author);

    }

//    Read
    public List<AuthorListResDto> findAll(){
        List<AuthorListResDto> dtoList = new ArrayList<>();
        for(Author author : repository.findAll())
            dtoList.add(
                    new AuthorListResDto(
                            author.getId(),
                            author.getName(),
                            author.getEmail()));
        return dtoList;
    }

    public AuthorDetailResDto findDetailById(Long id) {
        return makeResDto(findById(id));
    }

//    Update
    public AuthorDetailResDto update (Long id, AuthorUpdateReqDto dto){
        Author author = findById(id);
        author.updateAuthor(
                dto.getName(),
                dto.getPassword());
        return makeResDto(repository.save(author));
    }

//    Delete
    public void delete(Long id){repository.deleteById(id);}


    /* ECT */
    public Author findById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
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
                a.getCreatedTime(),
                roleString);
    }


}
