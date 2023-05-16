package nl.han.rwd.srd.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SRDAuthenticationFailureHandler implements AuthenticationFailureHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(SRDAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException ex) throws IOException
    {
        LOG.warn("Failed login by user: " + request.getParameter("username"));
        response.sendRedirect("/index");
    }
}
