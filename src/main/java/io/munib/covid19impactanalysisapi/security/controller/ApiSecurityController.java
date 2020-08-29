package io.munib.covid19impactanalysisapi.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.munib.covid19impactanalysisapi.security.jwt.utils.JwtUtils;
import io.munib.covid19impactanalysisapi.security.models.AuthenticationResponse;
import io.munib.covid19impactanalysisapi.security.models.MyUserDetails;
import io.munib.covid19impactanalysisapi.security.repository.MyUserDetailsRepository;

@RestController
public class ApiSecurityController {

	private JwtUtils jwtUtils;

	private MyUserDetailsRepository myUserDetailsRepository;

	public ApiSecurityController(MyUserDetailsRepository myUserDetailsRepository, JwtUtils jwtUtils) {
		this.myUserDetailsRepository = myUserDetailsRepository;
		this.jwtUtils = jwtUtils;
	}

	@RequestMapping("/authenticate/{username}")
	public ResponseEntity<?> authenticate(@PathVariable String username) throws Exception {

		String jwt = jwtUtils.generateToken(username.trim()).trim();

		MyUserDetails userInfoResult = myUserDetailsRepository.findByUsername(username);
		MyUserDetails info = userInfoResult != null ? new MyUserDetails(userInfoResult.getUsername(), jwt)
				: new MyUserDetails(username, jwt);
		myUserDetailsRepository.save(info);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
