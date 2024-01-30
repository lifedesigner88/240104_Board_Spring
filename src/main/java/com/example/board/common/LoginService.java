package com.example.board.common;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

    private final AuthorRepository repository;
    public LoginService (@Autowired AuthorRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//        인증 : 로그인 했냐 (authentication)
//        인가 : 권한이 있냐 (authorization)  (admin, user)

//        authorities

        Optional<Author> authorOptional = repository.findByEmail(email);
        if (authorOptional.isEmpty()) throw new UsernameNotFoundException("User not found");

        Author author = authorOptional.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
//        Role_권한 이 팬턴으로 스프링에서 기본적으로 권한 체크.
//        유저객체는 서버의 세션저장소 메모리에 저장된다.
//        authorities.add(new SimpleGrantedAuthority(author.getRole().name()));
        authorities.add(new SimpleGrantedAuthority("ROLE_" + author.getRole()));
        return new User(author.getEmail(), author.getPassword(), authorities);


//        쿠키 : JSESSIONID	7BC8D956B977969ECC64B8228BA5BABE
//        docker run -d -p 6379:6379 redis
//        docker ps
//        docker exec -it de9815859344 /bin/sh
//        # redis-cli
//        keys *




    }

}
