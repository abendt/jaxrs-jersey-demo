package arquillian

import org.jboss.arquillian.spock.ArquillianSputnik
import org.junit.runner.RunWith
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.web.context.ContextLoader
import spock.lang.Specification

@RunWith(ArquillianSputnik)
class AbstractInContainerSpecification extends Specification {

    def setup() {
        injectDependenciesIntoSpec()
    }

    private void injectDependenciesIntoSpec() {
        ApplicationContext rootCtx = ContextLoader.getCurrentWebApplicationContext()
        assert rootCtx

        AutowireCapableBeanFactory beanFactory = rootCtx.getAutowireCapableBeanFactory()
        assert beanFactory

        beanFactory.autowireBean(this)
    }
}
