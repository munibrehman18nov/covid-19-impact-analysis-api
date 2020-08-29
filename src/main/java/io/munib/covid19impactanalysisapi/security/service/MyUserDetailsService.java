package io.munib.covid19impactanalysisapi.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.munib.covid19impactanalysisapi.security.models.MyUserDetails;
import io.munib.covid19impactanalysisapi.security.repository.MyUserDetailsRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private MyUserDetailsRepository myUserDetailsRepository;

	@Autowired
	public MyUserDetailsService(MyUserDetailsRepository myUserDetailsRepository) {
		this.myUserDetailsRepository = myUserDetailsRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUserDetails info = myUserDetailsRepository.findByUsername(username);
		if (info != null) {
			return info;
		}
		throw new UsernameNotFoundException("User '" + username + "' not found");
	}

}
