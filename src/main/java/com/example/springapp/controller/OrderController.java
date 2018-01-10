package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.springapp.service.UserService;
import com.example.springapp.session.CartBean;
import com.example.springapp.model.Dish;
import com.example.springapp.model.User;
import com.example.springapp.repository.DishRepository;
import com.example.springapp.repository.UserRepository;
import java.util.List;

import javax.validation.Valid;

@Controller
@Scope("session")
public class OrderController {
	
	@Autowired
	CartBean cart;
	
	@Autowired
	DishRepository dishRepository;

	@RequestMapping(value = "/order")
	public String orderGET(Model model) {
		model.addAttribute("dishes", dishRepository.findAll());
		model.addAttribute("cartContent", cart.getDishList());
		return "order";
	}
	
	@RequestMapping(value = "/addToCart/{id}", method = RequestMethod.POST)
	public String addToCart(@PathVariable(value="id") Long id) {
		
		Dish dish = dishRepository.findOne(id);

		cart.addToCart(dish);
		for (Dish dL : cart.getDishList()){
			System.err.println(dL.getName());
		}
		return "redirect:/order";
	}
	/* Need to make deleting by objects, not by ID
	 * 
	 * 
	@RequestMapping(value = "/removeFromCart", method = RequestMethod.POST)
	public String removeFromCart(@RequestParam(value="cartItem") Dish dish) {

		cart.removeFromCart(dish);
		for (Dish dL : cart.getDishList()){
			System.err.println(dL.getName());
		}
		return "redirect:/order";
	}*/
	
	@RequestMapping(value = "/removeFromCart/{id}", method = RequestMethod.POST)
	public String removeFromCart(@PathVariable(value="id") Long id) {
		
		if (cart.findById(id) != null){
			cart.removeFromCart(cart.findById(id));
		}
		
		return "redirect:/order";
	}

}
