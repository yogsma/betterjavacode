package com.betterjavacode.sss.todolist.exceptions;

import org.springframework.security.core.AuthenticationException;

public class CustomOAuth2AuthenticationException extends AuthenticationException
{
    public CustomOAuth2AuthenticationException (String msg, Throwable t)
    {
        super(msg, t);
    }

    public CustomOAuth2AuthenticationException(String msg)
    {
        super(msg);
    }
}
