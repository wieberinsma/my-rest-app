package nl.han.rwd.srd.domain.user.spec.service;

import nl.han.rwd.srd.domain.user.AuthUser;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

public interface SecurityService
{
	String findLoggedInUsername();

	void updateCurrentUserSession(HttpSession session);

	Set<String> getUserAuthorities(AuthUser authUser);

	void updateAuthenticationAttributes(HttpSession session, String username);

	void clearAuthenticationAttributes(HttpSession request, List<String> attributes);
}

