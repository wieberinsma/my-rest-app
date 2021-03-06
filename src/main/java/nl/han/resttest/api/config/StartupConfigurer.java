package nl.han.resttest.api.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Properties;

@WebListener
public class StartupConfigurer implements ServletContextListener
{
    private static Properties dbProperties;

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        dbProperties = new Properties();
//        Class.forName("driverName");
        ServletContextListener.super.contextInitialized(sce);
    }

    public static Properties getDbProperties()
    {
        return dbProperties;
    }
}
