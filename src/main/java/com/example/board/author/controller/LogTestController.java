package com.example.board.author.controller;

import com.example.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

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



    @GetMapping("userinfo/test")
    public String userInfoTest(HttpServletRequest request){

//        로그인 유저 정보 얻는 방식
//        방법1. session에 attribute를 통해 접근.
        String email1 = request.getSession().getAttribute("email").toString();
        System.out.println(email1);

//        방법2. session에서 Authentication 객체를 접근
        Principal principal = request.getUserPrincipal();
        String email3 = principal.getName();
        System.out.println(email3);

        SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String email2 =  securityContext.getAuthentication().getName();
        System.out.println(email2);

//        방법3. SecurityContextHolder에서 Authentication객체 접근.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email4 = authentication.getName();
        System.out.println(email4);


        return null;
    }

}
