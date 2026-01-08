package com.slatdev.bankhelp.infrastructure.security;

import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.slatdev.bankhelp.application.service.AuthTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	private final HandlerExceptionResolver handlerExceptionResolver;
	private final AuthTokenService authTokenService;
	private final JpaUserDetailService jpaUserDetailService;

	public JwtAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver, AuthTokenService authTokenService,
			JpaUserDetailService jpaUserDetailService) {
		this.handlerExceptionResolver = handlerExceptionResolver;
		this.authTokenService = authTokenService;
		this.jpaUserDetailService = jpaUserDetailService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("[JWT_AUTHENTICATION_FILTER][DO_FILTER_INTERNAL] Inicio");
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			log.warn("[JWT_AUTHENTICATION_FILTER][DO_FILTER_INTERNAL] Autorizacion inexistente");
			filterChain.doFilter(request, response);
			return;
		}
		try {
			String jwt = authHeader.substring(7);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authTokenService.isTokenValid(jwt) && authentication == null) {
				String userName = authTokenService.extractUserName(jwt);
				UserDetails userdetails = jpaUserDetailService.loadUserByUsername(userName);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails,
						null, userdetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				String emailHash = DigestUtils.sha256Hex(userName);
				log.info("[JWT_AUTHENTICATION_FILTER][DO_FILTER_INTERNAL] Fin | emailHash={} | resultado=OK",
						emailHash);
			}
		} catch (Exception exception) {
			log.info("[JWT_AUTHENTICATION_FILTER][DO_FILTER_INTERNAL] Error al autenticar el usuario");
			handlerExceptionResolver.resolveException(request, response, authHeader, exception);
		}
		filterChain.doFilter(request, response);

		log.info("[JWT_AUTHENTICATION_FILTER][DO_FILTER_INTERNAL] Fin");
	}

}
