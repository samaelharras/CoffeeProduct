package com.ntg.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ntg.product.dto.CustomUser;
import com.ntg.product.model.Role;
import com.ntg.product.model.User;
import com.ntg.product.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder bcryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email);

		if (user == null)
			throw new UsernameNotFoundException("Email " + email + " Not found");

		return new CustomUser(user.getEmail(), 
				user.getPassword(),true, true, true,true,
				mapToGrantedAuthorities(user.getRoles()), user.getUserName());

	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles) {	
		if(roles != null && !roles.isEmpty()) {
			return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		}
		
		return new ArrayList<GrantedAuthority>();

	}
	
	public User createNewUser(User user) {
		if(user != null) {
			user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
			return userRepository.save(user);
		}
		
		return user;
	}

	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}
}