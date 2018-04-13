package hello;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class Database {

    public List<User> getUsers() {
        return Lists.newArrayList(
                new User(1, "Adrien", "adrien@example.com", User.Status.ACTIVE),
                new User(2, "Paul", "paul@example.com", User.Status.INACTIVE),
                new User(3, "Matthew", "matthew@example.com", User.Status.INACTIVE),
                new User(4, "Henry", "henry@example.com", User.Status.INACTIVE),
                new User(5, "Wu", "wu@example.com", User.Status.ACTIVE)
        );
    }

    public User getUser(long id) {
        for (User user : getUsers())
            if (user.getId() == id)
                return user;
        return null;
    }
}
