package com.example.board.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AopLogService {

//    @Pointcut(value = "execution(* com.example.board..controller..*.*(..))")
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerPointCut(){

    }


//    어드바이스들



    @Before("controllerPointCut()")
    public void beforeController(){
        log.info("[ --  Before executing controller method]");
//        메서드가 실행되기 전에 인증, 입력값 겆읃응을 수행하는 용도로 사용하는 단계
    }




    @After("controllerPointCut()")
    public void afterController(){
        log.info("[ --  After executing controller method]");
    }




    @Around("controllerPointCut()")
//    join point란 aop 대상으로 하는 컨트롤러의 특정 메스드를 의미
    public Object controllerLogger(ProceedingJoinPoint proceedingJoinPoint){

        log.info("[ --  request method" + proceedingJoinPoint.getSignature().toString());
        try {
//            본래의 컨트롤러 매서드 호출하는 부분.
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
