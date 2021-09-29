package services.impl;

import model.database.User;
import services.LoginService;
import services.database.UserRepository;

import javax.inject.Inject;
import java.util.UUID;

public class LoginServiceImpl implements LoginService
{
    @Inject
    private UserRepository userRepository;

    public User loginUser(String username, String password)
    {
        User existingUser = userRepository.findByUsernameAndPassword(username, password);
        if (existingUser != null)
        {
            String token = UUID.randomUUID().toString();
            existingUser.setToken(token);
            userRepository.update(existingUser);
            return existingUser;
        }

        return null;
    }
}
