package nl.han.resttest;

import nl.han.resttest.core.RootAppConfig;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class RestTestAppInitializer extends AbstractContextLoaderInitializer
{
    @Override
    protected WebApplicationContext createRootApplicationContext()
    {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootAppConfig.class);
        return rootContext;
    }
}
