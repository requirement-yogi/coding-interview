package hello;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import hello.User.Status;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * There is no database, this class mocks the behaviour of a database.
 */
public class Database {

    private static final ArrayList<User> TABLE_USERS = Lists.newArrayList(
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
        return Iterables.find(getUsers(), new Predicate<User>() {
            @Override
            public boolean apply(@Nullable User user) {
                return user.getId() == id;
            }
        }, null);
    }
}
