package hello;

import com.google.common.collect.Lists;
import hello.beans.Requirement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RequirementsController {

    private final List<String> users = Lists.newArrayList("John", "Henry", "Edward", "Paul");
    private final List<String> type = Lists.newArrayList("business-requirement", "functional-specification", "technical-requirement");
    private final List<String> status = Lists.newArrayList("IN PROGRESS", "ACCEPTED", "REJECTED");

    private final List<Requirement> requirements;
    private final Map<String, String> requirementStatuses;
    {
        requirements = new ArrayList<>();
        requirementStatuses = new HashMap<>();
        for (int i = 1; i < 600; i++) {
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
            requirement.addProperty("author", chooseRandom(users));
            requirement.addProperty("cost", chooseRandomCost());
            requirement.addProperty("type", chooseRandom(type));
            requirement.setDependencies(chooseRandomDependencies(requirement));
            requirementStatuses.put(requirement.getKey(), chooseRandom(status));
        }
    }

    /**
     * Returns all requirements
     */
    @RequestMapping("/rest/requirements")
    public List<Requirement> listRequirements() {
        return requirements;
    }

    /**
     * Returns all requirements
     */
    @RequestMapping("/rest/matrix")
    public List<Requirement> getMatrix() {
        return requirements;
    }

    /**
     * Returns the status of one requirement
     */
    @RequestMapping(value = "/rest/requirements/{key}/status", produces = "text/plain")
    public String getRequirementStatus(@PathVariable(value = "key") String requirementKey) {
        return requirementStatuses.get(requirementKey);
    }

    private List<String> chooseRandomDependencies(Requirement requirement) {
        List<String> dependencies;
        dependencies = Lists.newArrayList();
        for (int j = 0 ; j < Math.random() * 10 ; j++) {
            Requirement randomRequirement = requirements.get((int) (Math.random() * requirements.size()));
            String key = randomRequirement.getKey();
            if (randomRequirement != requirement && !dependencies.contains(key)) {
                dependencies.add(key);
            }
        }
        return dependencies;
    }

    private String chooseRandomCost() {
        return String.valueOf((int) (Math.random() * 3000));
    }

    private String chooseRandom(List<String> type) {
        return type.get((int) (Math.random() * type.size()));
    }

    private boolean chooseRandomBoolean(int chances) {
        return (int)(Math.random() * chances) == 0;
    }
}
