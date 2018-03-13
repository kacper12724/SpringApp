package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.springapp.service.UserService;
import com.example.springapp.model.User;
import com.example.springapp.repository.UserRepository;
import java.util.List;

import javax.validation.Valid;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/home")
	public String user(Model model) {
		model.addAttribute("users", userRepository.findAll());
		// Assert.notEmpty((List<User>) model.asMap().get("users"), "empty
		// model");
		return "home";
	}

	@RequestMapping("/users/show/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("user", userRepository.getOne(id));
		return "show";
	}

	@RequestMapping(value = "/users/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") Long id) {
		userRepository.delete(userRepository.findOne(id));
		return "redirect:/home";
	}

	@RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)
	public String editUserGET(@PathVariable("id") Long id, ModelMap modelMap) {
		modelMap.put("user", userRepository.findOne(id));
		return "editUser";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editUserPOST(@ModelAttribute("user") User user, ModelMap modelMap) {
		User tmpUser = userRepository.findOne(user.getUser_id());
		tmpUser.setPassword(user.getPassword());
		tmpUser.setUsername(user.getUsername());
		userRepository.save(tmpUser);
		return "redirect:/home";
	}

}
