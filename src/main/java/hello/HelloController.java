package hello;

import com.google.common.collect.Lists;
import hello.User.Status;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HelloController {

    private final Database database = new Database();

    @RequestMapping("/users")
    public List<User> listUsers(@RequestParam(value = "q", required = false) String query) {
        List<User> list = Lists.newArrayList(
                new User(1, "Adrien", "adrien@example.com", Status.ACCEPTED),
                new User(2, "Paul", "paul@example.com", Status.REJECTED),
                new User(3, "Matthew", "matthew@example.com", Status.INPROGRESS),
                new User(4, "Henry", "henry@example.com", User.Status.INPROGRESS),
                new User(5, "Wu", "wu@example.com", Status.ACCEPTED)
        );
        if (query != null) {
            list.removeIf(user -> !user.getEmail().contains(query));
        }
        return list;
    }

    @RequestMapping("/persons")
    public List<User> listPersons(@RequestParam(value = "q", required = false) String query) {
        if (query != null) {
            return database.searchUsers(query);
        } else {
            return Lists.newArrayList(
                    new User(1, "Adrien", "adrien@example.com", Status.ACCEPTED),
                    new User(2, "Paul", "paul@example.com", Status.REJECTED),
                    new User(3, "Matthew", "matthew@example.com", Status.INPROGRESS),
                    new User(4, "Henry", "henry@example.com", User.Status.INPROGRESS),
                    new User(5, "Wu", "wu@example.com", Status.ACCEPTED)
            );
        }
    }

    @RequestMapping("/people")
    public List<User> listPeople(@RequestParam(value = "q", required = false) String query) {
        return Lists.newArrayList(
                new User(1, "Adrien", "adrien@example.com", Status.ACCEPTED),
                new User(2, "Paul", "paul@example.com", Status.REJECTED),
                new User(3, "Matthew", "matthew@example.com", Status.INPROGRESS),
                new User(4, "Henry", "henry@example.com", User.Status.INPROGRESS),
                new User(5, "Wu", "wu@example.com", Status.ACCEPTED)
        )
                .stream()
                .filter(user -> query == null || user.getEmail().contains(query))
                .collect(Collectors.toList());
    }

    @RequestMapping("/folks")
    public List<User> listFolks() {
        return database.getUsers();
    }

    @RequestMapping(
            value = "/folks/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/JSON"}
    )
    public User updateFolks(@PathVariable long id, @RequestBody String status) {
        User.Status newStatus;
        if ("INPROGRESS".equals(status)) {
            newStatus = Status.ACCEPTED;
        } else {
            newStatus = Status.INPROGRESS;
        }
        User dbUser = database.getUser(id);
        dbUser.setStatus(newStatus);
        return dbUser;
    }

    @RequestMapping(
            value = "/folks/{id}",
            method = RequestMethod.POST,
            consumes = {"application/JSON"}
    )
    public User postFolks(@PathVariable long id, @RequestBody String status) {
        User.Status newStatus;
        if ("INPROGRESS".equals(status)) {
            newStatus = Status.ACCEPTED;
        } else {
            newStatus = Status.INPROGRESS;
        }
        User dbUser = database.getUser(id);
        dbUser.setStatus(newStatus);
        return dbUser;
    }

    @RequestMapping(
            value = "/people/{id}",
            method = RequestMethod.POST,
            consumes = {"application/JSON"}
    )
    public User changeStatus(@PathVariable long id, @RequestBody String status) {
        User.Status newStatus;
        if ("INPROGRESS".equals(status)) {
            newStatus = Status.REJECTED;
        } else {
            newStatus = Status.ACCEPTED;
        }
        User dbUser = database.getUser(id);
        dbUser.setStatus(newStatus);
        return dbUser;
    }

}
