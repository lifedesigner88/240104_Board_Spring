package com.example.board.author.repository;

// DataJpaTest 어노테이션을 사용하면 매 테스트가 종료되면 자동으로 DB 원상복구
// 모든 스프링 빈을 생성하지 않고, DB테스트 특화 어노테이션

// @SpringBootTest 자동 롤백 안하고 별도로 롤백 또는 어노테이션 필요.
// 모든 스프링 빈을 생성한다. (모든 스프링 실행과 동일하게 작동)

import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

// @DataJpaTest
 @SpringBootTest
 @Transactional
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void authorSaveTest(){


//      준비 (Given) 데이터
        String name = "testName";
        String email = "testEmail";
        String password = "test1234";
        Role role = Role.USER;

        String builderName = "builderName";
        String builderEmail = "builder";
        String builderPassword = "1234";
        Role builderRole = Role.ADMIN;

        Author author1 = new Author(
                name,
                email,
                password,
                role
        );

        Author author2 = Author.builder()
                .name(builderName)
                .email(builderEmail)
                .password(builderPassword)
                .role(builderRole)
                .build();

//        실행 (Excute,When)
        authorRepository.save(author1);
        authorRepository.save(author2);


//        검증 (Then)
        Author author1Test = authorRepository.findByEmail(email).orElse(null);
        assert author1Test != null;
        assertEquals(name, author1Test.getName());
        assertEquals(email, author1Test.getEmail());
        assertEquals(password, author1Test.getPassword());
        assertEquals(role, author1Test.getRole());

        Author author2test = authorRepository.findByEmail(builderEmail).orElse(null);
        assert author2test != null;
        assertEquals(builderName, author2test.getName());
        assertEquals(builderEmail, author2test.getEmail());
        assertEquals(builderPassword, author2test.getPassword());
        assertEquals(builderRole, author2test.getRole());


    }


}
