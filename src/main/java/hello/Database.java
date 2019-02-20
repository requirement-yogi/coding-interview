package hello;

import hello.utils.List2;
import hello.utils.Condition;
import hello.User.Status;

import java.util.List;

import static hello.utils.List2.f;

/**
 * There is no database, this class mocks the behaviour of a database.
 */
public class Database {

    private static final List2<User> TABLE_USERS = f(
        new User(1, "Adrien", "adrien@example.com", Status.ACTIVE),
        new User(2, "Paul", "paul@example.com", Status.INACTIVE),
        new User(3, "Matthew", "matthew@example.com", Status.INACTIVE),
        new User(4, "Henry", "henry@example.com", Status.INACTIVE),
        new User(5, "Wu", "wu@example.com", Status.SUSPENDED)
    );

    public List<User> getUsers() {
        return TABLE_USERS;
    }

    /**
     * Returns the user with id `id` from the database
     */
    public User getUser(long id) {
        User user = TABLE_USERS.find(new Condition<User>() {
            @Override
            public boolean filter(User item) {
                return item.getId() == id;
            }
        });
        return user;
    }
    
    public User getUser(String name) {
        if (name == null) return null;
        User user = TABLE_USERS.find(new Condition<User>() {
            @Override
            public boolean filter(User item) {
                return name.equals(item.getName());
            }
        });
        return user;
    }
}
