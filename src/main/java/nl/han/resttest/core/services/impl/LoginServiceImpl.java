package nl.han.resttest.core.services.impl;

import nl.han.resttest.core.model.User;
import nl.han.resttest.core.repositories.UserRepository;
import nl.han.resttest.core.services.LoginService;
import nl.han.resttest.database.model.UserEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService
{
    @Inject
    private UserRepository userRepository;

    @Override
    public User loginUser(User user)
    {
        User result = new User();

        UserEntity existingUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (existingUser != null)
        {
            String token = UUID.randomUUID().toString();
            existingUser.setToken(token);
            userRepository.save(existingUser);

            result.setToken(token);
            result.setFullName(existingUser.getFirstName() + existingUser.getLastName());

            return result;
        }

        return null;
    }
}
