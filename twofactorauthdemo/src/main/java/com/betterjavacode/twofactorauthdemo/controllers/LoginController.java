package com.betterjavacode.twofactorauthdemo.controllers;

import com.betterjavacode.twofactorauthdemo.dtos.ResetPasswordDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController
{
    @GetMapping
    public String login(@RequestParam(value = "error",defaultValue = "false") boolean loginError,
                        @RequestParam(value = "invalid-session", defaultValue = "false") boolean invalidSession,
                        final Model model, HttpSession session){

        String userName =getUserName(session);
        if(loginError){
            if(StringUtils.isNotEmpty(userName)){
                model.addAttribute("accountLocked", Boolean.TRUE);
                model.addAttribute("forgotPassword", new ResetPasswordDto());
                return "useraccount/login";
            }
        }
        if(invalidSession){
            model.addAttribute("invalidSession", "You already have an active session. We do not allow multiple active sessions");
        }
        model.addAttribute("forgotPassword", new ResetPasswordDto());
        model.addAttribute("accountLocked", Boolean.FALSE);
        return "useraccount/login";
    }

    final String getUserName(HttpSession session){
        final String username = (String) session.getAttribute("LAST_USERNAME");
        if(StringUtils.isNotEmpty(username)){
            session.removeAttribute("LAST_USERNAME");
        }
        return username;
    }

}
