package example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.net.URL;

@RunWith(Arquillian.class)
public class MyResourceTest {

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
                .addAsLibraries(files)
                .setWebXML("in-container-web.xml");
    }


    @Test
    public void shouldBeAbleToInvokeServletInDeployedWebApp() throws Exception {
        Client c = ClientBuilder.newClient();
        WebTarget target = c.target(baseURL + "/rest");
        String responseMsg = target.path("myresource").request().get(String.class);
        Assert.assertEquals("Got it!", responseMsg);
    }
}