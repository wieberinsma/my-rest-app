package core.config.database;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@ApplicationScoped
public class PersistenceConfig
{
    private static final Logger logger = Logger.getLogger(PersistenceConfig.class.getName());

    private static Properties properties;

    static {
        try
        {
            properties = new Properties();
            properties.load(PersistenceConfig.class.getClassLoader().getResourceAsStream("database.properties"));
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, "Can't access property file database.properties", e);
        }
    }

    public String getConnectionString()
    {
        return properties.getProperty("url") + "?user=" + properties.getProperty("user") +
                "&password=" + properties.getProperty("password") + "&serverTimezone=UTC";
    }

    public Properties getProperties() {
        return properties;
    }
}
