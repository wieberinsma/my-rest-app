package nl.han.rwd.srd.database.config;

import nl.han.rwd.srd.api.config.StartupConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Configuration of the Datasource and its connectivity characteristics. This datasource is injected in HibernateConfig.
 * Datasource is generically configured and specified in application.properties. This persistance configuration is
 * strong coupled to use of JDBC throught Spring Session, although a choice of drivers is allowed. Session timeout is
 * set to 1 hour and session tables are automatically managed by Spring. Only in Spring Boot are they also automatically
 * created.
 */
@Configuration
@PropertySource("classpath:application.properties")
@Import({HibernateConfig.class, SpringDataConfig.class})
public class PersistenceConfig
{
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(StartupConfigurer.getDbProperties().getProperty("driver"));
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}
