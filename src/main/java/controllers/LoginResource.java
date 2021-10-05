package controllers;

import model.api.LoginRequest;
import model.api.LoginResponse;
import model.database.User;
import services.LoginService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("")
public class LoginResource
{
    private static final Logger logger = Logger.getLogger(LoginResource.class.getName());

    @Inject
    private LoginService loginService;

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
