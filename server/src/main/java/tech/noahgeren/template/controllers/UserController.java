package tech.noahgeren.template.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.noahgeren.template.domain.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@GetMapping("/current")
	public User getCurrentUser(@AuthenticationPrincipal User user) {
		return user;
	}
	
}
