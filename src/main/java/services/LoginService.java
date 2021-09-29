package services;

import model.database.User;

public interface LoginService
{
    User loginUser(String username, String password);
}
