package hello.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the main entity, it is represented by a line in the table of a document.
 */
public class Requirement {

    private String key;
    private Map<String, String> properties = new HashMap<>();
    private List<String> dependencies = new ArrayList<>();

    public Requirement(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void addDependency(String requirement) {
        dependencies.add(requirement);
    }

    public void addProperty(String name, String value) {
        properties.put(name, value);
    }
}
