package com.example.board.post.repository;

import com.example.board.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedTimeDesc();

    // 쿼리 커스터마이징.

//  SELECT p.* FROM  post p LEFT JOIN author a on p.author_id = a.id;
//    jpql의 join 문은 author.객체를 통해 post를 스크리닝 하고 싶은 상황에 적합.
    @Query("SELECT p FROM Post p LEFT JOIN p.author")  // jpql 문
    List<Post> findAllJoin();


//  SELECT p.* a.* FROM  post p LEFT JOIN author a on p.author_id = a.id;
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.author")
    List<Post> findAllFetchJoin();
}
