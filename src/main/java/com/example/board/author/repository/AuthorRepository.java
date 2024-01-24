package com.example.board.author.repository;

import com.example.board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    //    findBy컬럼명의 규칙으로 자동으로 where 조건문을 사용한 메서드 생성
    //    findBy(컬럼A)and(컬럼B)의 규칙으로 자동으로 where 조건문을 사용한 메서드 생성
    //    findBy(Name)OR(Name)
    Optional<Author> findByEmail(String email);

}
