package nl.han.rwd.srd.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Configuration for the web (domain) resources and services. The resource handlers and locations are required for
 * mapping any of the resources referenced in Java. CSS files are included to allow both referencing from HTML as well
 * as bundling via Webpack.
 */
@Configuration
@EnableWebMvc
@Import(WebSecurityConfig.class)
@ComponentScan(basePackages = {"nl.han.rwd.srd.domain.user.impl.controller"})
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
        registry.addResourceHandler("/**.html", "/**.css", "/**.js", "/**.map")
                .addResourceLocations("classpath:/public/html/", "classpath:/static/css/", "classpath:/static/built/");
    }

    // TODO: Default to Thymeleaf templating engine to load up root HTML params
    @Bean
    public ViewResolver internalResourceViewResolver()
    {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setSuffix(".html");
        return bean;
    }
}
