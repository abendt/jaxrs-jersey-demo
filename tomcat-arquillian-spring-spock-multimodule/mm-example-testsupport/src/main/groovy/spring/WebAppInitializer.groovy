package spring

import org.springframework.core.annotation.Order
import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.request.RequestContextListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext

import javax.servlet.ServletContext

@Order(Integer.MIN_VALUE)
class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext =
            new AnnotationConfigWebApplicationContext()
        rootContext.register(ApplicationConfig.class)
        rootContext.scan("example")

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));
        container.addListener(new RequestContextListener())

        // The following line is required to avoid having jersey-spring3 registering it's own Spring root context.
        container.setInitParameter("contextConfigLocation", "")
    }
}