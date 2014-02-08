package spring

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration

@ComponentScan(basePackages = "example" )

@EnableTransactionManagement(proxyTargetClass = true)
class ApplicationConfig {

}
