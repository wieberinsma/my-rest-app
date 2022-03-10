package nl.han.resttest.core.controllers;

import nl.han.resttest.api.model.LoginRequest;
import nl.han.resttest.api.model.LoginResponse;
import nl.han.resttest.core.model.User;
import nl.han.resttest.core.services.LoginService;
import nl.han.resttest.database.model.UserEntity;
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

        var user = new User(loginRequest.getUser(), loginRequest.getPassword(),  null, null);

        try {
            UserEntity userEntity = loginService.loginUser(user);
            response.setUser(userEntity.getFirstName() + " " + userEntity.getLastName());
            response.setToken(userEntity.getToken());
        }
        catch (Exception e)
        {
            logger.severe("Failed to retrieve user with valid token.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
