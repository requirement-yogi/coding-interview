package hello;

import com.google.common.collect.Lists;

import java.util.List;

public class Constants {

    /**
     * In this software, there are only 3 types of requirements, the user can't choose them.
     */
    public static List<String> TYPE = Lists.newArrayList("business-requirement", "functional-specification", "technical-requirement");

    /**
     * In this software, requirements go through phases 1-to-7, except if they get rejected or cancelled, in which case
     * they go to 8 or 9.
     */
    public static List<String> STATUS = Lists.newArrayList(
            "1-SUGGESTION",
            "2-REFINED SUGGESTION",
            "3-PROJECT",
            "4-APPROVED",
            "5-IMPLEMENTED",
            "6-TESTED",
            "7-IN PRODUCTION",
            "8-REJECTED", // Rejected = The customer didn't approve one of the phases
            "9-CANCELLED" // Cancelled = The provider didn't want to implement it.
    );
}
