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
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

	private final LoginSuccessHandler oAuth2SuccessHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final ObjectMapper objectMapper;

	// @Bean
	// public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
	// 	return web -> web.ignoring()
	// 		.requestMatchers("/error")
	// 		.requestMatchers("/oauth2/**");
	// }

	// @Bean
	// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	// 	http
	// 		// rest api 설정
	// 		.csrf(AbstractHttpConfigurer::disable)
	// 		.cors(AbstractHttpConfigurer::disable)
	// 		.formLogin(AbstractHttpConfigurer::disable)
	// 		.logout(AbstractHttpConfigurer::disable)
	// 		.headers(c -> c.frameOptions(
	// 			HeadersConfigurer.FrameOptionsConfig::disable).disable()) // X-Frame-Options 비활성화
	// 		.sessionManagement(c ->
	// 			c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용하지 않음
	//
	// 		// request 인증, 인가 설정
	// 		.authorizeHttpRequests(request ->
	// 			request.requestMatchers(
	// 				new AntPathRequestMatcher("/"),
	// 				new AntPathRequestMatcher("/oauth2/**")
	// 			).permitAll()
	// 		.anyRequest().authenticated()
    //             )
	//
	// 	// // oauth2 설정
    //     //         .oauth2Login(oauth -> // OAuth2 로그인 기능에 대한 여러 설정의 진입점
	// 	// 	// OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
	// 	// 		oauth.userInfoEndpoint(c -> c.userService(oAuth2UserService))
	// 	// 		// 로그인 성공 시 핸들러
	// 	// 		.successHandler(oAuth2SuccessHandler)
	// 	// 	)
	//
	// 		// jwt 관련 설정
	// 		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	// 		.exceptionHandling((exceptions) -> exceptions
	// 			.authenticationEntryPoint(new AuthenticationExceptionHandler(objectMapper))
	// 			.accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper)));
	//
	// 	return http.build();
	// }
	@Bean
	@Order(1)
	SecurityFilterChain filterDefaultChain(HttpSecurity http) throws Exception {
		log.debug(">> filterDefaultChain activated");
		http.
			securityMatcher("/*")
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/oauth2/**").permitAll()
				.anyRequest().authenticated());
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

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