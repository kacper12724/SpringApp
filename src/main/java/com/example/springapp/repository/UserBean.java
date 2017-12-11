package com.example.springapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.springapp.model.User;
import com.example.springapp.service.UserService;

@Component
@Scope("session")
public class UserBean {
	
	@Autowired
	UserService userService;
	
    private User user;

    public User getCurrentUser(String username) {
        if (user == null) {
            user = userService.findByUsername(username);
        }
        return user;
    }
}