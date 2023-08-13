package com.betterjavacode.twofactorauthdemo.config.web;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails
{
    private String token;

    public CustomWebAuthenticationDetails (HttpServletRequest request)
    {
        super(request);
        this.token = request.getParameter("customToken");
    }
    @Override
    public String toString() {
        return "CustomWebAuthenticationDetails{" +
                "token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomWebAuthenticationDetails that = (CustomWebAuthenticationDetails) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
