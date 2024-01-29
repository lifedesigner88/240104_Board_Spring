package com.example.board.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class AopLogService {

//    @Pointcut(value = "execution(* com.example.board..controller..*.*(..))")
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerPointCut(){
    }



    @Before("controllerPointCut()")
    public void beforeController(JoinPoint joinPoint){
        log.info("[ --  Before executing controller method]");
//        메서드가 실행되기 전에 인증, 입력값 겆읃응을 수행하는 용도로 사용하는 단계

        ServletRequestAttributes servletReq =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        ObjectNode objectNode = getJsonNodes(joinPoint, servletReq);
        log.info(objectNode.toString());


    }

    @After("controllerPointCut()")
    public void afterController(){
        log.info("[ --  After executing controller method]");
    }



//    어드바이스.
    @Around("controllerPointCut()")
//    join point란 aop 대상으로 하는 컨트롤러의 특정 메스드를 의미
    public Object controllerLogger(ProceedingJoinPoint joinPoint){

//        log.info("[ --  request method" + joinPoint.getSignature().toString());
        ServletRequestAttributes servletReq =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        ObjectNode objectNode = getJsonNodes(joinPoint, servletReq);
        log.info(objectNode.toString());

        try {
//            본래의 컨트롤러 매서드 호출하는 부분.
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }



    private ObjectNode getJsonNodes(ProceedingJoinPoint joinPoint, ServletRequestAttributes servletReq) {
        HttpServletRequest req = servletReq.getRequest();

//        Json 형태로 사용자의 요청을 조립하기 위한 로직
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("Method Name", joinPoint.getSignature().getName());
        objectNode.put("CRUD Name", req.getMethod());
        Map<String, String[]> paramMap = req.getParameterMap();
        ObjectNode objectNodeDetail = objectMapper.valueToTree((paramMap));
        objectNode.set("user inputs", objectNodeDetail);
        return objectNode;
    }

    private ObjectNode getJsonNodes(JoinPoint joinPoint, ServletRequestAttributes servletReq) {
        HttpServletRequest req = servletReq.getRequest();

//        Json 형태로 사용자의 요청을 조립하기 위한 로직
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("Method Name", joinPoint.getSignature().getName());
        objectNode.put("CRUD Name", req.getMethod());
        Map<String, String[]> paramMap = req.getParameterMap();
        ObjectNode objectNodeDetail = objectMapper.valueToTree((paramMap));
        objectNode.set("user inputs", objectNodeDetail);
        return objectNode;
    }


}
