package services.database.impl;

import core.config.database.PersistenceConfig;
import model.database.User;
import services.database.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import java.sql.*;
import java.util.logging.Logger;

public class UserRepositoryImpl implements UserRepository
{
    private static final Logger logger = Logger.getLogger(UserRepositoryImpl.class.getName());

    @Inject
    private PersistenceConfig persistenceConfig;

    @Override
    public User findByUsernameAndPassword(String username, String password)
    {
        String query = "SELECT * FROM user_account WHERE username=" + username + " AND password=" + password;

        Connection connection = null;
        PreparedStatement statement = null;

        try
        {
            connection = DriverManager.getConnection(persistenceConfig.getConnectionString());
            statement = connection.prepareStatement(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.getFetchSize() > 1)
            {
                throw new NotAuthorizedException("Multiple users found with matching username and password.");
            }

            resultSet.next();

            User user = new User();
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));

            return user;
        }
        catch (SQLException e)
        {
            logger.severe("Failed to extract ResultSet.");
        }

        return null;
    }

    @Override
    public void update(User user)
    {
        // To be implemented
    }
}
