package nl.han.resttest.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Global method security allows the use of {@link org.springframework.security.access.prepost.PreAuthorize} to control
 * user authorization per endpoint.
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"nl.han.resttest.api.security"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .formLogin().loginPage("/index").permitAll()
            .and()
                .authorizeRequests().antMatchers("/dashboard").authenticated();
    }

}

