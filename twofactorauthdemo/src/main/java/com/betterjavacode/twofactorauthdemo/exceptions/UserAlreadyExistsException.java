package com.betterjavacode.twofactorauthdemo.exceptions;

public class UserAlreadyExistsException extends Exception
{
    public UserAlreadyExistsException() {
        super();
    }


    public UserAlreadyExistsException(String message) {
        super(message);
    }


    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
