package nl.han.resttest.core.config;

import nl.han.resttest.core.controllers.LoginController;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * _ResourceConfig_
 * Register the JAX-RS (Jersey) controllers
 */
@ApplicationPath("")
public class JerseyConfig extends ResourceConfig
{
    public JerseyConfig()
    {
        register(LoginController.class);
    }
}
