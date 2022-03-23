package nl.han.resttest;

import nl.han.resttest.api.config.WebConfig;
import nl.han.resttest.database.config.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"nl.han.resttest.domain"})
@Import({PersistenceConfig.class, WebConfig.class})
public class RootAppConfig
{
}
