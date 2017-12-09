package com.example.springapp.service;

import com.example.springapp.model.User;

public interface UserService {
	public User findByUsername(String username);
	public void saveUser(User user);
}