package nl.han.resttest.core.services;

import nl.han.resttest.api.model.LoginRequest;
import nl.han.resttest.core.model.User;
import nl.han.resttest.database.model.UserEntity;

public interface LoginService
{
    User loginUser(User user);

    User mapToUser(LoginRequest loginRequest);

    User mapToUser(UserEntity userEntity);
}
