package hello;

import com.google.common.collect.Lists;
import hello.beans.Requirement;
import hello.beans.User;
import hello.utils.List2;
import hello.utils.Condition;
import hello.beans.User.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static hello.Constants.STATUS;
import static hello.Constants.TYPE;
import static hello.utils.List2.f;

/**
 * There is no database, this class mocks the behaviour of a database. It contains:
 * - Users
 * - Requirements
 * - Their status
 *
 * It generates random data every time you restart the application.
 */
public class Database {

    private static final List2<User> TABLE_USERS = f(
            new User(1, "Adrien",  "adrien@example.com",  Status.ACCEPTED),
            new User(2, "Paul",    "paul@example.com",    Status.REJECTED),
            new User(3, "Matthew", "matthew@example.com", Status.INPROGRESS),
            new User(4, "Henry",   "henry@example.com",   Status.INPROGRESS),
            new User(5, "Wu",      "wu@example.com",      Status.ACCEPTED)
    );
    private final List<Requirement> requirements = generateData(50);

    /**
     * This simulates a SEPARATE data store, we pretend that it can't be merged with requirements
     */
    private final Map<String, String> requirementStatuses = generateStatuses(requirements); // Do not change / do not merge with requirements

    public List<User> getUsers() {
        return TABLE_USERS;
    }

    public List<Requirement> getAllRequirements() {
        return requirements;
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

    private static String chooseRandomCompletion() {
        return String.valueOf((int) (Math.random() * 100));
    }

    private static String chooseRandom(List<String> type) {
        return type.get((int) (Math.random() * type.size()));
    }

    private static boolean chooseRandomBoolean(int chances) {
        return (int)(Math.random() * chances) == 0;
    }

    private static List<String> chooseRandomDependencies(Requirement requirement, List<Requirement> list) {
        List<String> dependencies;
        dependencies = Lists.newArrayList();
        for (int j = 0 ; j < Math.random() * 10 ; j++) {
            Requirement randomRequirement = list.get((int) (Math.random() * list.size()));
            String key = randomRequirement.getKey();
            if (randomRequirement != requirement && !dependencies.contains(key)) {
                dependencies.add(key);
            }
        }
        return dependencies;
    }

    public String getRequirementStatus(String requirementKey) {
        return requirementStatuses.get(requirementKey);
    }

    private static List<Requirement> generateData(int size) {
        // Build a fake list of requirements
        List<Requirement> requirements = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            requirements.add(new Requirement(String.format("REQ-%04d", i)));
            if (chooseRandomBoolean(10)) {
                requirements.add(new Requirement(String.format("REQ-%04d-a", i)));
                if (chooseRandomBoolean(4)) {
                    for (int j = 1; chooseRandomBoolean(2) ; j++) {
                        requirements.add(new Requirement(String.format("REQ-%04d-a/%d", i, j)));
                    }
                }
                requirements.add(new Requirement(String.format("REQ-%04d-b", i)));
            }
        }
        for (Requirement requirement : requirements) {
            requirement.addProperty("author", chooseRandom(TABLE_USERS.stream().map(User::getName).collect(Collectors.toList())));
            requirement.addProperty("percentage", chooseRandomCompletion());
            requirement.addProperty("type", chooseRandom(TYPE));
            requirement.setDependencies(chooseRandomDependencies(requirement, requirements));
            //requirementStatuses.put(requirement.getKey(), chooseRandom(STATUS));
        }
        return requirements;
    }

    private Map<String, String> generateStatuses(List<Requirement> requirements) {
        Map<String, String> requirementStatuses = new HashMap<>();
        for (Requirement requirement : requirements) {
            requirementStatuses.put(requirement.getKey(), chooseRandom(STATUS));
        }
        return requirementStatuses;
    }

    public void setRequirementStatus(String requirementKey, String newStatus) {
        requirementStatuses.put(requirementKey, newStatus);
    }
}
