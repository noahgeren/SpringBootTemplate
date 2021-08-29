package tech.noahgeren.template.security;

import org.springframework.security.core.GrantedAuthority;

public enum AppAuthority implements GrantedAuthority {
	USER, ADMIN;

	@Override
	public String getAuthority() {
		return "ROLE_" + this.name();
	}

}
