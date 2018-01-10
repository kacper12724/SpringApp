package com.example.springapp.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.example.springapp.model.Dish;

@Component
@Scope(value="session", proxyMode =ScopedProxyMode.TARGET_CLASS)
public class CartBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<Dish> dishList = new ArrayList<>();
	
	public void addToCart(Dish dish){
		this.dishList.add(dish);
	}
	
	public void removeFromCart(Dish dish){
		this.dishList.remove(dish);
	}

	public List<Dish> getDishList() {
		return dishList;
	}

	public void setDishList(List<Dish> dishList) {
		this.dishList = dishList;
	}
	
	public Dish findById(Long id){
		for (Dish di : this.dishList){
			if (di.getId() == id){
				return di;
			}
		}
		return null;
	}

}