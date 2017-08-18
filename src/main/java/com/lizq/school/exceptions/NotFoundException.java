package com.lizq.school.exceptions;

/**
 * Created by lizq on 2017/8/15.
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }

}
