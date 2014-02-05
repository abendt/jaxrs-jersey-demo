package example.module1

import arquillian.AbstractSpecification
import example.core.MyService
import example.module1.MyResource
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.test.api.ArquillianResource
import org.jboss.shrinkwrap.api.spec.WebArchive

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget

class MyResourceSpec extends AbstractSpecification {

    @ArquillianResource
    private URL baseURL;

    /**
     * Define the deployment
     */
    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
        AbstractSpecification.baseDeployment().addClass(MyResource).addClass(MyService)
    }

    def setup() {
        assert baseURL
    }

    def "can use spock to test"() {
        given:
        Client c = ClientBuilder.newClient();
        WebTarget target = c.target(baseURL.toString() + "/webapi");

        when:
        String responseMsg = target.path("myresource").request().get(String.class);

        then:
        responseMsg == 'Hello World!'
    }
}
