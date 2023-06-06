package nl.han.rwd.srd.domain.user.impl.controller;

import nl.han.rwd.srd.api.exception.SRDEXception;
import nl.han.rwd.srd.api.exception.SRDExceptionMapper;
import nl.han.rwd.srd.api.model.RegistrationRequest;
import nl.han.rwd.srd.database.model.UserEntity;
import nl.han.rwd.srd.domain.user.spec.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class RegistrationController
{
    private static final Logger logger = Logger.getLogger(RegistrationController.class.getName());

    @Inject
    private UserRepository userRepository;

    @Inject
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Includes Hibernate validation for JSR 380 spec. Validation violations are handled via
     * {@link SRDExceptionMapper}.
     */
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest registrationRequest,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            // Handle validation errors
            String errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::toString)
                    .collect(Collectors.joining(","));

            return ResponseEntity.badRequest().body(errors);
        }

        try
        {
            UserEntity userEntity = mapToUserEntity(registrationRequest);
            userRepository.save(userEntity);
            return ResponseEntity.ok().body(null);
        }
        catch (Exception ex)
        {
            String error = "Failed to register new user.";
            logger.severe(error);
            throw new SRDEXception(error, ex);
        }
    }

    private UserEntity mapToUserEntity(RegistrationRequest registrationRequest)
    {
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setFirstName(registrationRequest.getFirstName());
        newUserEntity.setLastName(registrationRequest.getLastName());
        newUserEntity.setUsername(registrationRequest.getUsername());
        newUserEntity.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        return newUserEntity;
    }
}
