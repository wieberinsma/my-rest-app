package nl.han.rwd.srd.api.security;

import nl.han.rwd.srd.domain.user.spec.service.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

@Component
public class SRDAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    @Inject
    private SecurityService securityService;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException
    {
        handle(request, response);
    }

    /**
     * Initialize a new session with default authentication attributes. Cleans up previous log failures to prevent
     * authentication implementation interfering with old data.
     */
    private void handle(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60 * 30);
        securityService.updateAuthenticationAttributes(session, securityService.getAuthUsername());
        securityService.clearAuthenticationAttributes(session,
                Collections.singletonList(WebAttributes.AUTHENTICATION_EXCEPTION));

        if (!response.isCommitted())
        {
            redirectStrategy.sendRedirect(request, response, "/index");
        }
    }
}
