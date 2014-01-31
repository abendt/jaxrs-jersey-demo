package spock

import example.MyResource
import example.spring.ApplicationConfig
import example.spring.MyWebAppInitializer
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.arquillian.test.api.ArquillianResource
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget

@RunWith(ArquillianSputnik)
class MySpec extends Specification {

    @ArquillianResource
    private URL baseURL;

    /**
     * Define the deployment
     */
    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
        File[] files = Maven.resolver()
                .offline()
                .loadPomFromFile("pom.xml")
                .resolve("org.glassfish.jersey.containers:jersey-container-servlet-core",
                "com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider"
        )
                .withTransitivity()
                .asFile();

        return ShrinkWrap
                .create(WebArchive.class, "ROOT.war")
                .addClass(MyResource.class)
                .addClass(ApplicationConfig.class)
                .addClass(MyWebAppInitializer.class)
                .addAsLibraries(files)
                .setWebXML("in-container-web.xml");
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
        responseMsg == "Got it!"
    }
}
