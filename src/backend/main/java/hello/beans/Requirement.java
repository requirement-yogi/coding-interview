package hello.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Documents contain tables with a list of requirements. On requirement is
 * one line in such a table. A requirement has some text (in the cells of the tables),
 * it has some properties (each column is a property) and it has dependencies (if
 * someone puts a link to another requirement in a column, then it's a dependency).
 *
 * Here is an example of document: http://localhost:8080/adf-page-rendering.png
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

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    public void addProperty(String name, String value) {
        properties.put(name, value);
    }

    @Override
    public String toString() {
        return "Requirement{" + key + '}';
    }
}
