package nl.han.resttest.core.services.impl;

import nl.han.resttest.api.model.LoginRequest;
import nl.han.resttest.core.model.User;
import nl.han.resttest.core.repositories.UserRepository;
import nl.han.resttest.core.services.LoginService;
import nl.han.resttest.database.model.UserEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService
{
    @Inject
    private UserRepository userRepository;

    @Override
    public User loginUser(User user)
    {
        User result;

        List<UserEntity> userEntitiesList = userRepository.findAll();
//        UserEntity userEntity = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//        if (userEntity != null)
//        {
//            String token = UUID.randomUUID().toString();
//            userEntity.setToken(token);
//            userRepository.save(userEntity);
//
//            result = mapToUser(userEntity);
//
//            return result;
//        }

        for (int i = 0; i < userEntitiesList.size(); i++) {
            mapToUser(userEntitiesList.get(i));
        }

        return null;
    }

    @Override
    public User mapToUser(LoginRequest loginRequest)
    {
        User result = new User();
        result.setUsername(loginRequest.getUser());
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
