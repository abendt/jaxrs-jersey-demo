package example.core

import javax.inject.Named

@Named
class MyService {
    String sayHelloTo(String name) {
        "Hello $name"
    }
}
