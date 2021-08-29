package tech.noahgeren.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import tech.noahgeren.template.domain.User;
import tech.noahgeren.template.services.UserService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner{
	
	@Autowired
	private UserService userService;

	@Override
	public void run(String... args) throws Exception {
		userService.saveUser(new User("noahgeren", "test"), true);
	}

}
