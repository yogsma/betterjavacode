package com.betterjavacode.cognitodemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController
{
    @GetMapping("/")
    public String home(Model model, Principal principal)
    {
        model.addAttribute("username", principal.getName());
        return "index";
    }
}
