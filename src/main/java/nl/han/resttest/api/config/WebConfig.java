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
        registry.addResourceHandler("/**.html", "/**.css", "/**.js")
                .addResourceLocations("classpath:/public/html/", "classpath:", "classpath:/external/css/",
                                      "classpath:/static/built/");
    }

    // TODO: Default to Thymeleaf templating engine
    @Bean
    public ViewResolver internalResourceViewResolver()
    {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setSuffix(".html");
        return bean;
    }
}
