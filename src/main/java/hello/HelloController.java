package hello;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {

    private Database database = new Database();
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/users")
    public List<User> listUsers() {
        return Lists.newArrayList(
                new User(1, "Adrien", "adrien@example.com", User.Status.ACTIVE),
                new User(2, "Paul", "paul@example.com", User.Status.INACTIVE),
                new User(3, "Matthew", "matthew@example.com", User.Status.INACTIVE),
                new User(4, "Henry", "henry@example.com", User.Status.INACTIVE),
                new User(5, "Wu", "wu@example.com", User.Status.ACTIVE)
        );
    }

    @RequestMapping("/persons")
    public List<User> listPersons() {
        return Lists.newArrayList(
                new User(1, "Adrien", "adrien@example.com", User.Status.ACTIVE),
                new User(2, "Paul", "paul@example.com", User.Status.INACTIVE),
                new User(3, "Matthew", "matthew@example.com", User.Status.INACTIVE),
                new User(4, "Henry", "henry@example.com", User.Status.SUSPENDED),
                new User(5, "Wu", "wu@example.com", User.Status.ACTIVE)
        );
    }

    @RequestMapping("/people")
    public List<User> listPeople() {
        return Lists.newArrayList(
                new User(1, "Adrien", "adrien@example.com", User.Status.ACTIVE),
                new User(2, "Paul", "paul@example.com", User.Status.INACTIVE),
                new User(3, "Matthew", "matthew@example.com", User.Status.INACTIVE),
                new User(4, "Henry", "henry@example.com", User.Status.SUSPENDED),
                new User(5, "Wu", "wu@example.com", User.Status.ACTIVE)
        );
    }

    @RequestMapping("/folks")
    public List<User> listFolks() {
        return database.getUsers();
    }

    @RequestMapping(
            value = "/folks/{id}",
            method = RequestMethod.POST,
            consumes = {"application/JSON"}
    )
    public User updateFolks(@PathVariable long id, @RequestBody User user) {
        User dbUser = database.getUser(id);
        dbUser.setStatus(user.getStatus());
        return dbUser;
    }

}
