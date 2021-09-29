package services.database;

import model.database.User;

public interface UserRepository
{
    User findByUsernameAndPassword(String username, String password);

    void update(User user);

}
