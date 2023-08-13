package com.betterjavacode.twofactorauthdemo.config.handlers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
    private final GrantedAuthority adminAuthority = new SimpleGrantedAuthority(
            "ROLE_ADMIN");

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException
    {
        if(isAdminAuthority(authentication)){
            String targetUrl="home/admin";
            clearAuthenticationAttributes(request);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
        else{
            String targetUrl= determineTargetUrl(request, response);

            if(StringUtils.isEmpty(targetUrl) || StringUtils.equals(targetUrl, "/")){
                targetUrl= "/home";
            }
            clearAuthenticationAttributes(request);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);

            //You can let Spring security handle it for you.
            // super.onAuthenticationSuccess(request, response, authentication);
        }
    }



    protected boolean isAdminAuthority(final Authentication authentication)
    {
        return authentication.getAuthorities().size() == 0 && authentication.getAuthorities().contains(adminAuthority);
    }
}
