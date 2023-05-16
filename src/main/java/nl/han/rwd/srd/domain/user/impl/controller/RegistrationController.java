package nl.han.rwd.srd.domain.user.impl.controller;

import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
public class RegistrationController
{
    private static final Logger logger = Logger.getLogger(RegistrationController.class.getName());

    //TODO: Bcrypt password
//    @PostMapping(value = "/register")
//    public ResponseEntity<RegistrationResponse> login(@RequestBody RegistrationRequest registrationRequest)
//    {
//        var response = new RegistrationResponse();
//
//        var dataIn = registrationService.mapToUser(registrationRequest);
//
//        try {
////            var dataOut = registrationService.loginUser(dataIn);
//            mapToResponse(response, dataOut);
//        }
//        catch (Exception e)
//        {
//            logger.severe("Failed to login user.");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
}
