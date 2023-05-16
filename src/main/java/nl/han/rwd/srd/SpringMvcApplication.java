package nl.han.rwd.srd;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * {@link WebApplicationInitializer} | Bootstraps a Spring MVC web-application when Application-, ServletContext and
 * Servlet are properly configured. Context is configured using pre-determined @Configuration classes in a
 * hierarchical way (see @Import).
 */
public class SpringMvcApplication implements WebApplicationInitializer
{
    @Override
    public void onStartup(final ServletContext servletContext)
    {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootAppConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"))
                .addMappingForUrlPatterns(null, true, "/*");

        ServletRegistration.Dynamic appServlet =
                servletContext.addServlet("dispatcher", new DispatcherServlet(new GenericWebApplicationContext()));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
    }
}
