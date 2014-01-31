package example.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration

@ComponentScan(basePackages = "example",
        excludeFilters = {@ComponentScan.Filter(Configuration.class)})

@EnableTransactionManagement(proxyTargetClass = true)

public class ApplicationConfig {

    @Bean
    String bean1() {
        return "Got it!";
    }
}
