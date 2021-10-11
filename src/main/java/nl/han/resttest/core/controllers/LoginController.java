package nl.han.resttest.core.controllers;

import nl.han.resttest.api.model.LoginRequest;
import nl.han.resttest.api.model.LoginResponse;
import nl.han.resttest.core.repositories.UserRepository;
import nl.han.resttest.core.services.LoginService;
import nl.han.resttest.database.model.User;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("")
public class LoginController
{
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Inject
    private LoginService loginService;

    @Inject
    private UserRepository userRepository;

    @POST
    @Path("login")
    public Response login(LoginRequest loginRequest)
    {
        var response = new LoginResponse();

        try {
            User dbUser = loginService.loginUser(loginRequest.getUser(), loginRequest.getPassword());
            response.setUser(dbUser.getFirstName() + " " + dbUser.getLastName());
            response.setToken(dbUser.getToken());
        }
        catch (Exception e)
        {
            logger.severe("Failed to retrieve user with valid token.");
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok().entity(response).build();
    }
}
