package com.lizq.school.exceptions;

/**
 * Created by lizq on 2017/8/15.
 */
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
