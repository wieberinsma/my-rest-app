package nl.han.resttest.domain.user.impl.controller;

import nl.han.resttest.api.model.LoginRequest;
import nl.han.resttest.api.model.LoginResponse;
import nl.han.resttest.domain.user.impl.model.User;
import nl.han.resttest.domain.user.spec.service.LoginService;
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

        var dataIn = loginService.mapToUser(loginRequest);

        try {
            var dataOut = loginService.loginUser(dataIn);
            mapToResponse(response, dataOut);
        }
        catch (Exception e)
        {
            logger.severe("Failed to login user.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private void mapToResponse(LoginResponse response, User responseData)
    {
        response.setUser(responseData.getFullName());
        response.setToken(responseData.getToken());
    }
}
