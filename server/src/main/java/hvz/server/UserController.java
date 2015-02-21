package hvz.server;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/user")
    public UserResource UserResource(@RequestParam(value="name", defaultValue="World") String name) {
        return new UserResource(counter.incrementAndGet(), String.format(template, name));
    }
}