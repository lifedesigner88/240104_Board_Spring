package com.example.board.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;


// ControllerAdvice 와 ExceptionHandler를 통해 예외처리의 공통화 로직 작성

@ControllerAdvice
@Slf4j
public class CommonException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        log.error("Entity not found : " + ex.getMessage());
        return this.errRsponseMessage(HttpStatus.NOT_FOUND, "요청한 내용을 찾을 수 없습니다");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleEntityIliegalArgument(IllegalArgumentException ex) {
        log.error("Entity not found : " + ex.getMessage());
        return this.errRsponseMessage(HttpStatus.NOT_ACCEPTABLE, "잘못된 접근 입니다. ");
    }

    public ResponseEntity<Map<String, Object>>
    errRsponseMessage(HttpStatus status, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", Integer.toString(status.value()));
        map.put("status message", status.getReasonPhrase());
        map.put("error message", message);
        return new ResponseEntity<>(map, status);
    }

}
