package io.munib.covid19impactanalysisapi.security.jwt.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.munib.covid19impactanalysisapi.security.jwt.utils.JwtUtils;
import io.munib.covid19impactanalysisapi.security.models.MyUserDetails;
import io.munib.covid19impactanalysisapi.security.repository.MyUserDetailsRepository;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private String jwtHeaderPrefix = "Bearer";

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private MyUserDetailsRepository myUserDetailsRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;

		if (authorizationHeader != null && authorizationHeader.startsWith(jwtHeaderPrefix)) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtils.extractUsername(jwt);
		}

		if (username != null) {
			MyUserDetails userInfo = myUserDetailsRepository.findByUsername(username);

			if (userInfo != null && userInfo.getPassword().equals(jwt)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						username, jwt);
				authenticationManager.authenticate(usernamePasswordAuthenticationToken);
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
