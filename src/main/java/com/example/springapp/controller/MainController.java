package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.springapp.model.User;
import com.example.springapp.service.UserService;
import com.example.springapp.validator.UserValidator;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private UserValidator userValidator;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerGET(ModelMap modelMap){
		modelMap.put("user", new User());
		return "registerUser";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUserPOST(@ModelAttribute ("user") User user,
			ModelMap modelMap, BindingResult bindingResult) {
		//userRepository.save(user);
		
		//userService.saveUser(user);
		//return "redirect:/home";
		//--------------------
		userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registerUser";
        }

        userService.saveUser(user);
		return "redirect:/home";
	}
	
	@RequestMapping(value={"/login"})
    public String login(){
        return "login";
    }
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcomeGET() {
		return "welcome";
	}

}