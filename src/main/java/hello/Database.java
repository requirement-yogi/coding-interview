package hello;

import hello.beans.User;
import hello.utils.List2;
import hello.utils.Condition;
import hello.beans.User.Status;

import java.util.ArrayList;
import java.util.List;

import static hello.utils.List2.f;

/**
 * There is no database, this class mocks the behaviour of a database.
 */
public class Database {

    private static final List2<User> TABLE_USERS = f(
            new User(1, "Adrien",  "adrien@example.com",  Status.ACCEPTED),
            new User(2, "Paul",    "paul@example.com",    Status.REJECTED),
            new User(3, "Matthew", "matthew@example.com", Status.INPROGRESS),
            new User(4, "Henry",   "henry@example.com",   Status.INPROGRESS),
            new User(5, "Wu",      "wu@example.com",      Status.ACCEPTED)
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

    public List<User> searchUsers(String substringOfEmail) {
        if (substringOfEmail == null) return null;
        List<User> searchResults = new ArrayList<>();
        for (User user : TABLE_USERS)
            if (contains(substringOfEmail, user.getEmail()))
                searchResults.add(user);
        return searchResults;
    }

    /** Returns true if 'text' contains 'substring', and false otherwise */
    private static boolean contains(String substring, String text) {
        return text.contains(substring);
    }
}
