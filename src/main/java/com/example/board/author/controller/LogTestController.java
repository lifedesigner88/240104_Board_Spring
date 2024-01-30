package com.example.board.author.controller;

import com.example.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
// slf4j 어노테이션(롬복)을 통해 쉽게 logback 로그 라이브러리 사용가능.
@Slf4j
public class LogTestController {

    public final AuthorService service;
    public LogTestController(@Autowired AuthorService service) {
        this.service = service;
    }


//    slfj 어노테이션 없이 직접 라이브러리 import 하여 로거 생성 가능.
//    private static final Logger log = LoggerFactory.getLogger(LogTestController.class);
    @GetMapping("/log/test1")
    public String testMethod1() {

        log.debug("디버그 로그입니다.");
        log.info("인포 로그입니다.");
        log.error("에러 로그입니다.");

        return "OK";
    }

    @GetMapping("/exception/test1/{id}")
    public String exceptiontestMethod(@PathVariable Long id) {

//        try {
            service.findById(id);
//
//        } catch (Exception e) {
//            log.error("**** Exception occurred: {}", e.getMessage());
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
//        }

        return "OK";
    }

}
