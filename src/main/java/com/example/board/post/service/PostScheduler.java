package com.example.board.post.service;

import com.example.board.post.domain.Post;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class PostScheduler {

    private final PostRepository repository;
    public PostScheduler(@Autowired PostRepository repository) {
        this.repository = repository;
    }

/*
    초 분 시간 일 월 요일 (6개) 형태로 스케줄링 설정
    * : 매 초 (분/시 등)을 의미
    특정 숫자: 특정 숫자의 초 (분/시 등을 의미)
    0/특정숫자 : 특정숫자마다
       초     분  시간 일  월  요일
    ex) 0     0   *   *   *   * : 매일 0분 0초에 스케줄링 시작 시작
    ex) 0/1   0   *   *   *   * :
    ex) 0     0/1 *   *   *   * :
*/

    @Scheduled(cron = "0 0/1 * * * *")
    @Transactional
    public void postSchedule(){
        System.out.println("== 스케줄러 시작 ==");
        Page<Post> posts = repository.findByAppointment("Y", Pageable.unpaged());
        LocalDateTime now = LocalDateTime.now();
        for (Post post : posts)
            if(post.getAppointmentTime().isBefore(now))
                post.setAppointment(null, now);
    }
//    Transactional 붙여도 잘 저장된다. save

}
