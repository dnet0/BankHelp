package com.slatdev.bankhelp.infrastructure.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

	private final JpaUserDetailService jpaUserDetailService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	public WebSecurityConfig(JpaUserDetailService jpaUserDetailService, JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jpaUserDetailService = jpaUserDetailService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable()) // Mientras tenemos el token con jwt
		.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/**").permitAll()
				.requestMatchers("/api/tickets/mine").authenticated()
	            .requestMatchers("/api/tickets", "/api/tickets/", "/api/tickets/**").hasRole("AGENT") 
				.anyRequest().authenticated()
				)
		.userDetailsService(jpaUserDetailService)
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}