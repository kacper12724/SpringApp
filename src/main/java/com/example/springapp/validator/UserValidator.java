package com.example.springapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.springapp.model.User;
import com.example.springapp.service.UserService;

@Component
public class UserValidator implements Validator {
	private static Pattern emailNamePtrn = Pattern
			.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	public static boolean validateEmailAddress(String email) {
		Matcher mtch = emailNamePtrn.matcher(email);
		if (mtch.matches()) {
			return true;
		}
		return false;
	}

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;

		if (userService.findByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", "Duplicate.user.username");
		}

		if (!validateEmailAddress(user.getEmail())) {
			errors.rejectValue("email", "Invalid.user.email");
		}
	}
}