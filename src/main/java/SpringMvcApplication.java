import nl.han.resttest.core.RootAppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class SpringMvcApplication implements WebApplicationInitializer
{
    @Override
    public void onStartup(final ServletContext servletContext)
    {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootAppConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));

        ServletRegistration.Dynamic appServlet =
                servletContext.addServlet("dispatcher", new DispatcherServlet(new GenericWebApplicationContext()));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
    }
}
