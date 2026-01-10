package com.slatdev.bankhelp.infrastructure.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfig {

	private final JpaUserDetailService jpaUserDetailService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public WebSecurityConfig(JpaUserDetailService jpaUserDetailService,
			JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jpaUserDetailService = jpaUserDetailService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable()) // Mientras tenemos el token con jwt
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/auth/**").permitAll().requestMatchers("/api/tickets/mine")
								.authenticated().requestMatchers("/api/tickets", "/api/tickets/", "/api/tickets/**")
								.hasRole("AGENT").anyRequest().authenticated())
				.userDetailsService(jpaUserDetailService)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", // React
				"http://localhost:4200", // Angular
				"http://localhost:4321" // Vite
		));
		configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS"));

		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));

		configuration.setExposedHeaders(Arrays.asList("Authorization"));
		
		configuration.setAllowCredentials(true);
		
	    configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}