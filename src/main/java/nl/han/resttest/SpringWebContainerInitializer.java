package nl.han.resttest;

import nl.han.resttest.core.RootAppConfig;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringWebContainerInitializer implements WebApplicationInitializer
{
    @Override
    public void onStartup(ServletContext servletContext)
    {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(RootAppConfig.class);

        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.setInitParameter("contextConfigLocation", "");

        context.setServletContext(servletContext);
    }
}
