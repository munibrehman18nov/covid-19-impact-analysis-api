package io.munib.covid19impactanalysisapi.security.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Entity
public class MyUserDetails implements UserDetails {

	@Id
	private String username;
	private String token;

	public MyUserDetails() {
	}

	public MyUserDetails(String username, String token) {
		this.username = username;
		this.token = token;
		System.out.println("2");
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return this.token;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String toString() {
		return "username: " + this.getUsername() + ", password: " + this.getPassword() + ", isAccountNonExpired: "
				+ this.isAccountNonExpired() + ", : isAccountNonLocked: " + this.isAccountNonExpired()
				+ ", isCredentialsNonExpired: " + this.isCredentialsNonExpired() + ", isEnabled: " + this.isEnabled();
	}

}
