package com.example.springapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.springapp.model.User;
import com.example.springapp.repository.UserRepository;
import com.example.springapp.service.EmailSender;
import com.example.springapp.service.UserService;
import com.example.springapp.session.CartBean;
import com.example.springapp.validator.UserValidator;
import com.example.springapp.model.Dish;

@Controller
public class EmailController {

	@Autowired
	EmailSender emailSender;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CartBean cart;
	

	@RequestMapping(value = "/send_email_order", method = RequestMethod.POST)
	public String sendOrder(Locale locale) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(auth.getName());
		String order = "";
		int fullPrice = 0;
		for (Dish d : cart.getDishList()){
			order += d.getName() + ": " + d.getPrice() + "PLN\n";
			fullPrice += d.getPrice();
		}
		emailSender.sendEmail(user.getEmail(), 
				messageSource.getMessage("email_subject_order", new Object[] {}, locale),
				"Twoj nick to: " + user.getUsername() + "\n"
				+ "Twoje zamowienie: " + order + "\n Cena: " + fullPrice + "PLN");
		return "redirect:/welcome";
	}
	
	@RequestMapping(value = "/send_email_register", method = RequestMethod.POST)
	public String sendRegister(Locale locale, @ModelAttribute("user") User user, ModelMap modelMap, BindingResult bindingResult) {
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registerUser";
		}
		
		Random ran = new Random();
		Integer x = ran.nextInt(100)+1;
		String xString = x.toString();
		
		user.setRegcode(xString);
		user.setStatus("I");

		userService.saveUser(user);
		emailSender.sendEmail(user.getEmail(), 
				messageSource.getMessage("email_subject_register", new Object[] {}, locale),
				"Witamy w serwisie. Ponizej link do aktywacji konta.\n" 
				+ "http://localhost:8080/app/register/" + xString);
		return "redirect:/home";
	}
}