package com.example.springapp.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.springapp.model.User;
import com.example.springapp.repository.UserBean;
import com.example.springapp.repository.UserRepository;
import com.example.springapp.service.EmailSender;
import com.example.springapp.service.UserService;

@Controller
public class EmailController {

    @Autowired
    EmailSender emailSender;
    
    @Autowired
    UserRepository userRepository;
    
    UserBean userBean;

    @RequestMapping(value="/send_email", method = RequestMethod.POST)
    public String send() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userRepository.findByUsername(auth.getName());
        emailSender.sendEmail(user.getEmail(), "Testowy temat", "Twoj nick to: " + user.getUsername());
        return "redirect:/welcome";
    }
}