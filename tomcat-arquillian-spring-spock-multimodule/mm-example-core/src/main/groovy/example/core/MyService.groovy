package example.core

import javax.inject.Inject
import javax.inject.Named

@Named
class MyService {

    @Inject
    String hello

    String sayHelloTo(String name) {
        "$hello $name"
    }
}
