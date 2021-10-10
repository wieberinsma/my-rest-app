package nl.han.resttest.core.services.impl;

import nl.han.resttest.core.repositories.UserRepository;
import nl.han.resttest.core.services.LoginService;
import nl.han.resttest.database.model.User;

import javax.inject.Inject;
import java.util.UUID;

public class LoginServiceImpl implements LoginService
{
//    @Inject
//    private UserRepository userRepository;

    @Override
    public User loginUser(String username, String password)
    {
//        User existingUser = userRepository.findByUsernameAndPassword(username, password);
//        if (existingUser != null)
//        {
//            String token = UUID.randomUUID().toString();
//            existingUser.setToken(token);
//            userRepository.update(existingUser);
//            return existingUser;
//        }
//
        return null;
    }
}
