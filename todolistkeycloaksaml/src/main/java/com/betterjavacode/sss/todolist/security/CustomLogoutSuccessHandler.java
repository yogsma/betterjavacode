package com.betterjavacode.sss.todolist.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler
{
    public static final Logger LOGGER = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess (HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        LOGGER.info("Enter >> onLogoutSuccess()");
        if (authentication != null && authentication.getDetails() != null)
        {
            LOGGER.info("authentication info found.");
            try
            {
                request.getSession().invalidate();
            }
            catch (Exception e)
            {
                LOGGER.debug("Exception during logout " + e.getStackTrace());
                throw new RuntimeException("Could not logout due to " + e.getLocalizedMessage());
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
        LOGGER.info("Exit << onLogoutSuccess()");
    }
}
