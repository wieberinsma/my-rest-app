package nl.han.resttest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Configuration for the web (domain) resources and services.
 * <p>
 * Features:
 * - CORS mapping: self-explanatory
 * - Resource handler: to define all file patterns and locations available for the web context
 * - Resource view resolver: together with above handler allows for String-based resolving of HTML templates
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"nl.han.resttest.domain.user.impl.controller"})
public class WebConfig implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry
                .addResourceHandler("/**.html", "/**.js")
                .addResourceLocations("classpath:/pages/", "classpath:/js/");
    }

    @Bean
    public ViewResolver internalResourceViewResolver()
    {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setSuffix(".html");
        return bean;
    }
}
