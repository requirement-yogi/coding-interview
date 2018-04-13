package hello;

import com.google.common.collect.Lists;
import hello.User.Status;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {

    private Database database = new Database();

    @RequestMapping("/users")
    public List<User> listUsers() {
        return Lists.newArrayList(
                new User(1, "Adrien", "adrien@example.com", User.Status.ACTIVE),
                new User(2, "Paul", "paul@example.com", User.Status.SUSPENDED),
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

    /**
     * This REST resource, deployed at "POST /folks/{id}", takes the current status of the user and
     * decides of the new status.
     */
    @RequestMapping(
            value = "/folks/{id}",
            method = RequestMethod.POST,
            consumes = {"application/JSON"}
    )
    public User updateFolks(@PathVariable long id, @RequestBody String status) {
        User.Status newStatus;
        if ("ACTIVE".equals(status)) {
            newStatus = Status.INACTIVE;
        } else {
            newStatus = Status.ACTIVE;
        }
        User dbUser = database.getUser(id);
        dbUser.setStatus(newStatus);
        return dbUser;
    }

}
