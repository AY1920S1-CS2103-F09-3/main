package seedu.address.testutil;

import seedu.address.model.Color;
import seedu.address.model.module.Module;

public class TypicalModule {
    public static final Module CS1101S = new ModuleBuilder()
            .withModuleCode("CS1101S")
            .withName("Programming Methodology")
            .withColor(Color.RED)
            .withPrereqsSatisfied(true)
            .withPrereqTree()
            .withMcCount(4)
            .withTags("Core", "Completed")
            .build();
    public static final Module CS2102 = new ModuleBuilder()
            .withModuleCode("CS2102")
            .withName("Database Systems")
            .withColor(Color.RED)
            .withPrereqsSatisfied(true)
            .withPrereqTree()
            .withMcCount(4)
            .withTags("Database")
            .build();
    public static final Module ST2334 = new ModuleBuilder()
            .withModuleCode("ST2334")
            .withName("Probability and Statistics")
            .withColor(Color.RED)
            .withPrereqsSatisfied(true)
            .withPrereqTree()
            .withMcCount(4)
            .withTags("Core")
            .build();
    public static final Module CS3244 = new ModuleBuilder()
            .withModuleCode("CS3244")
            .withName("Machine Learning")
            .withColor(Color.RED)
            .withPrereqsSatisfied(false)
            .withPrereqTree()
            .withMcCount(4)
            .withTags("Cool", "AI", "ML")
            .build();
    public static final Module CS5219 = new ModuleBuilder()
            .withModuleCode("CS5219")
            .withName("Automated Software Validation\n")
            .withColor(Color.RED)
            .withPrereqsSatisfied(false)
            .withPrereqTree()
            .withMcCount(4)
            .withTags("SWE")
            .build();
    public static final Module CS5339 = new ModuleBuilder()
            .withModuleCode("CS5339")
            .withName("Theory and Algorithms for Machine Learning\n")
            .withColor(Color.RED)
            .withPrereqsSatisfied(false)
            .withPrereqTree()
            .withMcCount(4)
            .withTags("SWE")
            .build();

    private TypicalModule() {
    } // prevents instantiation
}
