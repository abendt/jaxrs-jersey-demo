package example;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MyService {

    @Inject
    String bean1;

    public String service() {
        return bean1;
    }
}
