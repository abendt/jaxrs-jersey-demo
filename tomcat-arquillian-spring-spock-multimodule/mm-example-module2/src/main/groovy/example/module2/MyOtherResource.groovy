package example.module2

import example.core.MyService

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path('otherresource')
class MyOtherResource {

    @Inject
    MyService myService

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String getIt() {
        myService.sayHelloTo('someone else!')
    }

}
