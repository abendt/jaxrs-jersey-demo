package example.core

import arquillian.AbstractInContainerSpecification
import arquillian.DeploymentBuilder
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.shrinkwrap.api.spec.WebArchive

import javax.inject.Inject

class MyServiceSpec extends AbstractInContainerSpecification {

    @Inject
    MyService myService

    @Deployment
    public static WebArchive createTestArchive() {
        new DeploymentBuilder().build().addClass(MyService)
    }

    def setup() {
        assert myService
    }

    def "can use spock to test"() {
        expect:
        myService.sayHelloTo('Test') == 'Hello Test'
    }
}
