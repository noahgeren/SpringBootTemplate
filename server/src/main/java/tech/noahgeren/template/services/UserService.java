package tech.noahgeren.template.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tech.noahgeren.template.dao.UserRepository;
import tech.noahgeren.template.domain.User;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder pwEncoder;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}
	
	public User saveUser(User user) {
		return saveUser(user, false);
	}
	
	public User saveUser(User user, boolean hashPassword) {
		if(hashPassword) {
			user.setPassword(pwEncoder.encode(user.getPassword()));
		}
		return userRepo.save(user);
	}

}
