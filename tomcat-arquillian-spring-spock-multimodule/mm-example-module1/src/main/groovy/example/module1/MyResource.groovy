package example.module1

import example.core.MyService

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path('myresource')
class MyResource {

    @Inject
    MyService myService

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String getIt() {
        myService.sayHelloTo('World!')
    }
}
