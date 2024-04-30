package com.example.peer.security.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.peer.security.exception.SecurityErrorCode;
import com.example.peer.security.exception.SecurityException;
import com.example.peer.security.utils.JwtTokenProvider;
import com.example.peer.user.entity.Role;
import com.example.peer.user.entity.User;
import com.example.peer.user.repository.UserRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByName(username);
		if (optionalUser.isEmpty()){
			throw new SecurityException(SecurityErrorCode.USER_NOT_FOUND);
		}
		User user = optionalUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
		log.debug(">> loadUserByUsername() called, found successfully");

		return new org.springframework.security.core.userdetails.User(user.getName(), "password", authorities);

	}

	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()){
			throw new SecurityException(SecurityErrorCode.USER_NOT_FOUND);
		}
		User user = optionalUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();


		authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
		log.debug(">> loadUserByUsername() called, found successfully");

		return new org.springframework.security.core.userdetails.User(user.getName(), "password", authorities);

	}

}