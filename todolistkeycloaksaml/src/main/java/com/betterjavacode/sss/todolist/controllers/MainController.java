package com.betterjavacode.sss.todolist.controllers;

import com.betterjavacode.sss.todolist.dtos.UserDto;
import com.betterjavacode.sss.todolist.managers.UsersManager;
import com.betterjavacode.sss.todolist.models.Users;
import com.betterjavacode.sss.todolist.repositories.UsersRepository;
import com.betterjavacode.sss.todolist.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController
{

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersManager usersManager;


    @GetMapping(value="/index")
    public String getHomePage(Model model,
                              @AuthenticationPrincipal Saml2AuthenticatedPrincipal saml2AuthenticatedPrincipal)
    {
        String principal = saml2AuthenticatedPrincipal.getName();
        model.addAttribute("username", principal);
        return "index";
    }

    @RequestMapping(value={"/","/login"}, method={RequestMethod.POST, RequestMethod.GET})
    public String authenticateUser(HttpServletRequest request,
                                   HttpServletResponse response, Model model) throws IOException
    {
        LOGGER.info("Enter >> authenticateUser()");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken) ) //&&     !(authentication instanceof OAuth2AuthenticationToken)
        {
            String name = authentication.getName();

            Users user = usersRepository.findUserByEmail(name);

            if(user == null)
            {
                LOGGER.info("User not found");
                return "forward:/";
            }
            LOGGER.info("User found : {} ", user.getFirstName());

            model.addAttribute("username", user.getFirstName() + " " + user.getLastName()) ;
            LOGGER.info("Exit << authenticateUser()");
            return "index";
        }
        LOGGER.info("Exit << authenticateUser()");
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup()
    {
        return "signup";
    }

    @RequestMapping(value = "/signup", method=RequestMethod.POST, params="action=Cancel")
    public String signupCancel()
    {
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, params="action=Submit")
    public String signupSubmission(HttpServletRequest httpServletRequest)
    {
        String email = httpServletRequest.getParameter( "email");
        String password = httpServletRequest.getParameter( "password");
        String confirmPassword = httpServletRequest.getParameter( "confirmpassword");
        String firstname = httpServletRequest.getParameter("firstname");
        String lastname = httpServletRequest.getParameter( "lastname");


        if(!password.equals(confirmPassword))
        {
            throw new RuntimeException("Passwords do not match");
        }

        String encodedPassword = passwordEncoder.encode(password);

        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setFirstName(firstname);
        userDto.setLastName(lastname);
        userDto.setPassword(encodedPassword);
        userDto.setRole("ADMIN");
        userDto.setEnabled(true);

        UserDto returnedUser = usersManager.createUser(userDto);


        return "signupconfirmation";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        LOGGER.info("Logging the user out...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null)
        {
            LOGGER.info("Authentication = {}", authentication.getName());
            CookieUtils.deleteCookie(request, response, "oauth2_auth_request");
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        else
        {
            throw new RuntimeException("No Authentication info found.");
        }
        return "redirect:/login";
    }

}
