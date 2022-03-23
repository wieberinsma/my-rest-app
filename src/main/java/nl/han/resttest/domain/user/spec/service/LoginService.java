package nl.han.resttest.domain.user.spec.service;

import nl.han.resttest.api.model.LoginRequest;
import nl.han.resttest.domain.user.impl.model.User;
import nl.han.resttest.database.model.UserEntity;

public interface LoginService
{
    User loginUser(User user);

    User mapToUser(LoginRequest loginRequest);

    User mapToUser(UserEntity userEntity);
}
