package hello;

import com.google.common.collect.Lists;
import hello.beans.Requirement;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RequirementsController {

    private final List<String> users = Lists.newArrayList("John", "Henry", "Edward", "Paul");

    private final List<Requirement> requirements;
    {
        requirements = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            requirements.add(new Requirement(String.format("REQ-%04d", i)));
        }
        for (int i = 0; i < requirements.size() ; i++) {
            Requirement requirement = this.requirements.get(i);
            requirement.addProperty("author", users.get((int) (Math.random() * users.size())));
            requirement.addProperty("cost", String.valueOf((int) (Math.random() * 3000)));
            for (int j = 0 ; j < Math.random() * 10 ; j++) {
                Requirement randomRequirement = requirements.get((int) (Math.random() * requirements.size()));
                String key = randomRequirement.getKey();
                if (randomRequirement != requirement && !requirement.getDependencies().contains(key)) {
                    requirement.addDependency(key);
                }
            }
        }
    }

    @RequestMapping("/rest/requirements")
    public List<Requirement> listRequirements(@RequestParam(value = "q", required = false) String query) {
        return requirements;
    }
}
