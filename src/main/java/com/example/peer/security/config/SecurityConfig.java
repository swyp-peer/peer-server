package com.example.peer.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.peer.security.filter.JwtAuthenticationFilter;
import com.example.peer.security.handler.AuthenticationExceptionHandler;
import com.example.peer.security.handler.CustomAccessDeniedHandler;
import com.example.peer.security.handler.LoginSuccessHandler;
import com.example.peer.security.utils.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;
	private final ObjectMapper objectMapper;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
		return web -> web.ignoring()
			.requestMatchers("/error")
			.requestMatchers("/oauth2/**");
	}
	@Bean
	@Order(1)
	SecurityFilterChain filterDefaultChain(HttpSecurity http) throws Exception {
		log.debug(">> filterDefaultChain activated");
		http.
			securityMatcher("/**")
			.authorizeHttpRequests(authorize -> authorize
				// .requestMatchers("/oauth2/**").permitAll()
				.anyRequest().authenticated());
		http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, objectMapper), UsernamePasswordAuthenticationFilter.class);

		http.exceptionHandling(
			exceptionHandler -> {
				exceptionHandler.authenticationEntryPoint(new AuthenticationExceptionHandler(objectMapper));
				exceptionHandler.accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper));

			});

		return http.build();
	}
	// @Bean
	// @Order(2)
	// SecurityFilterChain loginFilterChain(HttpSecurity httpSecurity) throws Exception{
	// 	log.debug(">> loginFilterChain activated");
	// 	httpSecurity.securityMatcher("/oauth2/**")
	// 		.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
	// 	return httpSecurity.build();
	// }
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}