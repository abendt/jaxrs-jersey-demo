package example;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import javax.annotation.Nullable;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {


    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {

        List<Observable<String>> commands = new ArrayList<>();

        for (int x = 0; x < 4; ++x) {
            commands.add(new AlwaysFailsCommand().observe());
            commands.add(new AlwaysSuccessCommand().observe());
            commands.add(new SometimesCommand().observe());
        }

        final StringBuilder builder = new StringBuilder();

        Observable.merge(Lists.transform(commands, mapErrorMessage()))
                .toBlockingObservable().forEach(new Action1() {
            public void call(Object s) {
                builder.append(s);
                builder.append('\n');
            }
        });

        return builder.toString();
    }

    private Function<Observable<String>, Observable<String>> mapErrorMessage() {
        return new Function<Observable<String>, Observable<String>>() {
            @Nullable
            @Override
            public Observable<String> apply(@Nullable Observable<String> input) {
                return input.onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable error) {
                        return error.getMessage();
                    }
                });
            }
        };
    }

    class AlwaysSuccessCommand extends HystrixCommand<String> {

        public AlwaysSuccessCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("HelloGroup"));
        }

        @Override
        protected String run() {
            latency();
            // a real example would do work like a network call here

            System.out.println("ALWAYS OK");

            return "ALWAYS";
        }
    }

    class AlwaysFailsCommand extends HystrixCommand<String> {

        public AlwaysFailsCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("HelloGroup"));
        }

        @Override
        protected String run() {
            latency();

            System.out.println("ALWAYS FAILS");

            throw new RuntimeException("EX AlWAYS");
        }
    }

    static final Random random = new Random(System.currentTimeMillis());

    private void latency() {
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
        }
    }

    class SometimesCommand extends HystrixCommand<String> {

        protected SometimesCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("HelloGroup"));
        }

        @Override
        protected String run() throws Exception {
            if (random.nextInt(10) < 2) {
                System.out.println("SOMETIMES FAILS");

                throw new RuntimeException("EX SOME");
            }

            latency();

            System.out.println("SOMETIMES OK");

            return "SOMETIMES";
        }
    }
}
