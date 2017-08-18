package com.lizq.school.exceptions;

import com.lizq.school.mappers.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.jws.WebResult;

/**
 * Created by lizq on 2017/8/15.
 * 该类用来处理controller中的全局异常
 */
@ControllerAdvice(basePackages = "com.lizq.school.controller")
public class GlobalExceptionHandler {

    //请求错误异常
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequestException(WebRequest request, Exception ex){
        return ResponseEntity.badRequest().body(new Message(ex.getMessage()));
    }

    //未找到异常
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleFoundException(WebRequest request,Exception ex){
        return  ResponseEntity.notFound().build();
    }



}
