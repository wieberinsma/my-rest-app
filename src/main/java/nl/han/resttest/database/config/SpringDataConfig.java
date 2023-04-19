package nl.han.resttest.database.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * {@link EnableJpaRepositories} | Auto-configures JPA Repository implementations for all domain tables, allowing
 * method-based querying through declaration of @Repository interfaces.
 */
@Configuration
@EnableJpaRepositories(basePackages = "nl.han.resttest.domain")
public class SpringDataConfig
{
}
