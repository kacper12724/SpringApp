package com.example.springapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.springapp.model.User;
import com.example.springapp.repository.UserRepository;
import com.example.springapp.service.UserService;
import com.example.springapp.session.CartBean;
import com.example.springapp.validator.UserValidator;

@Controller
public class MainController {
	
	@Autowired
	CartBean cart;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;


	@Autowired
	private UserValidator userValidator;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerGET(ModelMap modelMap) {
		modelMap.put("user", new User());
		return "registerUser";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUserPOST(@ModelAttribute("user") User user, ModelMap modelMap, BindingResult bindingResult) {
		// userRepository.save(user);

		// userService.saveUser(user);
		// return "redirect:/home";
		// --------------------
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registerUser";
		}

		userService.saveUser(user);
		return "redirect:/home";
	}

	@RequestMapping(value = { "/login" })
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcomeGET(Model model) {
		model.addAttribute("cartContent", cart.getDishList());
		return "welcome";
	}
	
	@RequestMapping(value = "/register/{code}", method = RequestMethod.GET)
	public String registerLink(@PathVariable(value="code") String code, Model model) {
		model.addAttribute("regCode", code);
		User user = userRepository.findByRegcodev(code);
		if (user != null){
			System.err.println("1 + " + user.getRegcode() + user.getStatus());
			if (user.getStatus().equals("I"))
				userRepository.activateAccount(user.getUsername(), code, "A");
			//userRepository.activateAccount(user.getUsername(), code);
		} else {
		}
		return "home";
	}

}
