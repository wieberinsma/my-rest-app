package nl.han.resttest.api.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class SRDAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		Authentication result = super.authenticate(auth);
		return new UsernamePasswordAuthenticationToken(result.getPrincipal(), result.getCredentials(),
				result.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
