package nl.han.resttest.core;

import nl.han.resttest.database.config.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"nl.han.resttest.core.controllers", "nl.han.resttest.core.services"})
@Import({PersistenceConfig.class})
public class RootAppConfig
{
}
