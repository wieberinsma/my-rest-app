package nl.han.resttest.api.config;

import nl.han.resttest.api.security.SRDAccessDeniedHandler;
import nl.han.resttest.api.security.SRDAuthenticationDetailsSource;
import nl.han.resttest.api.security.SRDAuthenticationFailureHandler;
import nl.han.resttest.api.security.SRDAuthenticationProvider;
import nl.han.resttest.api.security.SRDAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;

/**
 * Global method security allows the use of {@link org.springframework.security.access.prepost.PreAuthorize} to control
 * user authorization per endpoint.
 *
 * If using 'anyRequest().authenticated()' this will result in incorrect MIME-type.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"nl.han.resttest.api.security"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private SRDAuthenticationDetailsSource authDetailsSource;

    @Inject
    private SRDAccessDeniedHandler accessDeniedHandler;

    @Inject
    private SRDAuthenticationSuccessHandler successHandler;

    @Inject
    private SRDAuthenticationFailureHandler failureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        SRDAuthenticationProvider authProvider = new SRDAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

//    @Bean
//    public SessionRegistry sessionRegistry()
//    {
//        return new SessionRegistryImpl();
//    }

//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher()
//    {
//        return new HttpSessionEventPublisher();
//    }

    @Bean
    public CorsConfigurationSource corsConfiguration()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("/*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
//        .sessionManagement()
//            .maximumSessions(1)
//            .sessionRegistry(sessionRegistry())
//            .and()
//            .sessionFixation()
//            .migrateSession()

        //TODO: Logoutsucceshandler ipv logoutsuccesurl (net als login failure, succes)

        http
            .authenticationProvider(authProvider())
                .formLogin()
                    .loginPage("/index")
                    .loginProcessingUrl("/login")
                        .permitAll()
                .authenticationDetailsSource(authDetailsSource)
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
            .and()
                .authorizeRequests()
                    .antMatchers("/index")
                        .permitAll()
                    .antMatchers("/dashboard")
                        .authenticated()
            .and()
                .rememberMe()
                .key("rememberMeSecretKey")
                .tokenValiditySeconds(86400)
            .and()
                .cors()
                    .configurationSource(corsConfiguration())
            .and()
                .csrf()
                    .disable()
                .requestCache()
                    .disable();
    }
}

