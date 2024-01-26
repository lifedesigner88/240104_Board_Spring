package com.example.board.post.service;

import com.example.board.post.domain.Post;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public class PostScheduler {

    private final PostRepository repository;
    public PostScheduler(@Autowired PostRepository repository) {
        this.repository = repository;
    }


    public void postSchedule(){
        System.out.println("== 스케줄러 시작 ==");
        Page<Post> posts = repository.findByAppointment("Y", Pageable.unpaged());
        LocalDateTime now = LocalDateTime.now();
        for (Post post : posts)
            if(post.getAppointmentTime().isBefore(now)) {
                post.setAppointment(null, now);
                repository.save(post);
            }

    }



}
