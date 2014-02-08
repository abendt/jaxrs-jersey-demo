package arquillian

import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven
import spring.ApplicationConfig
import spring.WebAppInitializer

class DeploymentBuilder {

    def mavenDeps = [
            'org.glassfish.jersey.containers:jersey-container-servlet:2.5.1',
            'com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider:2.3.1'
    ]

    def archiveName = 'ROOT.war'

    def webXml = 'in-container-web.xml'

    def classes = [
            ApplicationConfig,
            WebAppInitializer
    ]

    WebArchive build() {
        File[] libs = Maven.resolver()
                .offline()
                .loadPomFromFile("pom.xml")
                .resolve(mavenDeps)
                .withTransitivity()
                .asFile()

        return ShrinkWrap
                .create(WebArchive, archiveName)
                .addClasses(classes as Class[])
                .addAsLibraries(libs)
                .setWebXML(webXml);
    }
}
