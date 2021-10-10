package nl.han.resttest.core;

import nl.han.resttest.database.config.PersistenceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Import({PersistenceConfig.class})
public class RootAppConfig
{
}
