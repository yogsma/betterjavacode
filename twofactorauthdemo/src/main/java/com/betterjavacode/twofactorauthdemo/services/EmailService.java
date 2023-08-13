package com.betterjavacode.twofactorauthdemo.services;


import com.betterjavacode.twofactorauthdemo.context.AbstractEmailContext;

import javax.mail.MessagingException;

public interface EmailService
{
    void sendMail(final AbstractEmailContext email) throws MessagingException;
}
