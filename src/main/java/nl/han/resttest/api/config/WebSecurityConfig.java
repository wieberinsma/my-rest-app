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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = { "nl.han.resttest.api.security" })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private SRDAuthenticationDetailsSource authDetailsSource;

    @Inject
    private SRDAuthenticationSuccessHandler successHandler;

    @Inject
    private SRDAuthenticationFailureHandler failureHandler;

    @Inject
    private SRDAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SessionRegistry sessionRegistry()
    {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher()
    {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        SRDAuthenticationProvider authProvider = new SRDAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

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

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
			.sessionManagement()
				.maximumSessions(1)
				.sessionRegistry(sessionRegistry())
				.and()
					.sessionFixation()
						.migrateSession()
			.and()
				.authenticationProvider(authProvider())
				.formLogin()
				.authenticationDetailsSource(authDetailsSource)
				.loginProcessingUrl("/login")
					.loginPage("/login")
					.permitAll()
				.successHandler(successHandler)
				.failureHandler(failureHandler)
			.and()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler)
			.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/login")
					.deleteCookies("JSESSIONID")
					.invalidateHttpSession(true)
			.and()
				.authorizeRequests()
					.antMatchers("/", "/login")
						.permitAll()
					.anyRequest()
						.authenticated()
			.and()
				.rememberMe()
					.key("rememberMeSecretKey")
					.tokenValiditySeconds(86400)
			.and()
				.cors().configurationSource(corsConfiguration())
			.and()
				.csrf()
					.disable()
				.requestCache()
					.disable();
    }

}

