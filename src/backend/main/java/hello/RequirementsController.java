package hello;

import com.google.common.collect.Lists;
import hello.beans.Requirement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hello.Constants.STATUS;

@RestController
public class RequirementsController {

    final static private Database database = new Database();

    /**
     * Returns all requirements
     */
    @RequestMapping("/rest/requirements")
    public List<Requirement> listRequirements() {
        return database.getAllRequirements();
    }

    /**
     * Returns all requirements
     */
    @RequestMapping("/rest/matrix")
    public List<Requirement> getMatrix() {
        return database.getAllRequirements();
    }

    /**
     * Returns the status of one requirement
     */
    @RequestMapping(value = "/rest/requirements/{key}/status", produces = "text/plain")
    public String getRequirementStatus(@PathVariable(value = "key") String requirementKey) {
        return database.getRequirementStatus(requirementKey);
    }

    /**
     * Returns the status of one requirement
     */
    @RequestMapping(
            value = "/rest/requirements/{key}/status",
            method = RequestMethod.POST,
            produces = "text/plain"
    )
    public String changeRequirementStatus(@PathVariable(value = "key") String requirementKey) {
        String oldStatus = database.getRequirementStatus(requirementKey);
        // This complicated operation just takes the next status in the list, and rolls over at the end
        String newStatus = STATUS.get((STATUS.indexOf(oldStatus) + 1 ) % STATUS.size());
        database.setRequirementStatus(requirementKey, newStatus);
        return newStatus;
    }
}
