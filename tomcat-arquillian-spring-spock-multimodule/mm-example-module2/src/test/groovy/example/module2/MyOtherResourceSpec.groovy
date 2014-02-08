package example.module2

import arquillian.DeploymentBuilder
import example.core.CoreConfig
import example.core.MyService
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.arquillian.test.api.ArquillianResource
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget

@RunWith(ArquillianSputnik)
class MyOtherResourceSpec extends Specification {

    @ArquillianResource
    private URL baseURL;

    /**
     * Define the deployment
     */
    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
        new DeploymentBuilder().build().addClass(MyOtherResource).addClass(MyService).addClass(MyService).addClass(CoreConfig)
    }

    def setup() {
        assert baseURL
    }

    def "can use spock to test"() {
        given:
        Client c = ClientBuilder.newClient();
        WebTarget target = c.target(baseURL.toString() + "/webapi");

        when:
        String responseMsg = target.path("otherresource").request().get(String.class);

        then:
        responseMsg == 'Hello someone else!'
    }
}
