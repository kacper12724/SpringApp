package com.example.springapp.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.springapp.model.User;
import com.example.springapp.repository.UserRepository;
import com.example.springapp.service.EmailSender;
import com.example.springapp.session.CartBean;

@Controller
public class EmailController {

	@Autowired
	EmailSender emailSender;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MessageSource messageSource;

	CartBean userBean;

	@RequestMapping(value = "/send_email", method = RequestMethod.POST)
	public String send(Locale locale) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(auth.getName());
		emailSender.sendEmail(user.getEmail(), messageSource.getMessage("email_subject", new Object[] {}, locale),
				"Twoj nick to: " + user.getUsername());
		return "redirect:/welcome";
	}
}