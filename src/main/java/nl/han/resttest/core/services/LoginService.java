package nl.han.resttest.core.services;

import nl.han.resttest.database.model.User;

public interface LoginService
{
    User loginUser(String username, String password);
}
