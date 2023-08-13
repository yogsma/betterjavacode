package com.betterjavacode.twofactorauthdemo.controllers;

import com.betterjavacode.twofactorauthdemo.dtos.MfaTokenDto;
import com.betterjavacode.twofactorauthdemo.dtos.UserDto;
import com.betterjavacode.twofactorauthdemo.exceptions.InvalidTokenException;
import com.betterjavacode.twofactorauthdemo.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller()
@RequestMapping("/register")
public class RegistrationController
{
    private static final String REDIRECT_LOGIN= "redirect:/login";

    @Resource
    private UserService userService;

    @Resource
    private MessageSource messageSource;

    @GetMapping
    public String register(final Model model){
        model.addAttribute("userData", new UserDto());
        return "useraccount/register";
    }

    @PostMapping
    public String userRegistration(final UserDto userData, final BindingResult bindingResult,
                                   final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userData", userData);
            return "useraccount/register";
        }
        try {
            userService.register(userData);
            MfaTokenDto mfaData = userService.mfaSetup(userData.getEmail());
            model.addAttribute("qrCode", mfaData.getQrCode());
            model.addAttribute("qrCodeKey", mfaData.getMfaCode());
            model.addAttribute("qrCodeSetup", true);
        } catch (Exception e) {
            bindingResult.rejectValue("email", "userData.email","An account already exists for this email.");
            model.addAttribute("userData", userData);
            return "useraccount/register";
        }
        model.addAttribute("registrationMsg", "Thanks for your registration. We have sent a " +
                "verification email. Please verify your account.Please scan the QR code for generating MFA token for login.");
        return "useraccount/register";
    }

    @GetMapping("/verify")
    public String verifyCustomer(@RequestParam(required = false) String token, final Model model, RedirectAttributes redirAttr){
        if(StringUtils.isEmpty(token)){
            redirAttr.addFlashAttribute("tokenError", "Token is empty");
            return REDIRECT_LOGIN;
        }
        try {
            userService.verifyUser(token);
        } catch (InvalidTokenException e) {
            redirAttr.addFlashAttribute("tokenError", "Token is invalid. Provide a valid token.");
            return REDIRECT_LOGIN;
        }

        redirAttr.addFlashAttribute("verifiedAccountMsg", "Your account is verified. You can " +
                "login now");
        return REDIRECT_LOGIN;
    }
}
