package arquillian

import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven
import org.junit.runner.RunWith
import spock.lang.Specification
import spring.ApplicationConfig
import spring.WebAppInitializer

@RunWith(ArquillianSputnik)
class AbstractSpecification extends Specification {

    static WebArchive baseDeployment() {
        File[] files = Maven.resolver()
                .offline()
                .loadPomFromFile("pom.xml")
                .resolve("org.glassfish.jersey.containers:jersey-container-servlet:2.5.1",
                "com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider:2.3.1"
        )
                .withTransitivity()
                .asFile();

        return ShrinkWrap
                .create(WebArchive, "ROOT.war")
                .addClass(ApplicationConfig)
                .addClass(WebAppInitializer)
                .addAsLibraries(files)
                .setWebXML("in-container-web.xml");
    }
}
