package hello;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/users")
    public List<User> listUsers() {
        return Lists.newArrayList(
            new User("Adrien", "adrien@example.com", User.Status.ACTIVE),
            new User("Paul", "paul@example.com", User.Status.INACTIVE),
            new User("Matthew", "matthew@example.com", User.Status.INACTIVE),
            new User("Wu", "wu@example.com", User.Status.ACTIVE)
        );
    }

}
