package nl.han.resttest.database.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "nl.han.resttest.core.repositories")
public class SpringDataConfig
{
}
