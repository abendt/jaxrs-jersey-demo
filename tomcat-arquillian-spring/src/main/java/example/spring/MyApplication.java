package example.spring;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.ws.rs.Path;
import java.util.HashSet;
import java.util.Map;

public class MyApplication extends ResourceConfig {
    public MyApplication() {
        ApplicationContext rootCtx = ContextLoader.getCurrentWebApplicationContext();

        Map result = rootCtx.getBeansWithAnnotation(Path.class);

        registerInstances(new HashSet(result.values()));
    }
}
