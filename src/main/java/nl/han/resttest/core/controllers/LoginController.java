package nl.han.resttest.core.controllers;

import nl.han.resttest.api.model.LoginRequest;
import nl.han.resttest.api.model.LoginResponse;
import nl.han.resttest.core.services.LoginService;
import nl.han.resttest.database.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.logging.Logger;

@RestController
public class LoginController
{
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Inject
    private LoginService loginService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest)
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
