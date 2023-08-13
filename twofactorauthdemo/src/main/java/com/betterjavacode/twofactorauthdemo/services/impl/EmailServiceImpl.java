package com.betterjavacode.twofactorauthdemo.services.impl;

import com.betterjavacode.twofactorauthdemo.context.AbstractEmailContext;
import com.betterjavacode.twofactorauthdemo.services.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class EmailServiceImpl implements EmailService
{
    @Override
    public void sendMail (AbstractEmailContext email) throws MessagingException
    {

    }
}
