package example.core

import arquillian.AbstractSpecification
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.springframework.context.ApplicationContext
import org.springframework.web.context.ContextLoader

class MyServiceSpec extends AbstractSpecification {

    MyService myService

    @Deployment
    public static WebArchive createTestArchive() {
        AbstractSpecification.baseDeployment().addClass(MyService)
    }

    def setup() {
        ApplicationContext rootCtx = ContextLoader.getCurrentWebApplicationContext();
        myService = rootCtx.getBean(MyService)

        assert myService
    }

    def "can use spock to test"() {
        expect:
        myService.sayHelloTo('Test') == 'Hello Test'
    }
}
