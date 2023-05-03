package nl.han.resttest.domain.user.impl;

import nl.han.resttest.api.model.LoginRequest;
import nl.han.resttest.domain.user.impl.model.User;
import nl.han.resttest.domain.user.spec.repository.UserRepository;
import nl.han.resttest.domain.user.spec.service.LoginService;
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
        User result;

        UserEntity userEntity = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (userEntity != null)
        {
            String token = UUID.randomUUID().toString();
            userEntity.setToken(token);
            userRepository.save(userEntity);

            result = mapToUser(userEntity);

            return result;
        }

        return null;
    }

    @Override
    public User mapToUser(LoginRequest loginRequest)
    {
        User result = new User();
        result.setUsername(loginRequest.getUsername());
        result.setPassword(loginRequest.getPassword());
        return result;
    }

    @Override
    public User mapToUser(UserEntity userEntity)
    {
        User result = new User();
        result.setFullName(userEntity.getFirstName(), userEntity.getLastName());
        result.setToken(userEntity.getToken());
        return result;
    }

}
