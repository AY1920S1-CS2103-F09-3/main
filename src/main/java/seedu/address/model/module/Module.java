package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.model.Color;
import seedu.address.model.PrereqTree;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;

/**
 * Represents a module for CS undergraduate students.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module implements Cloneable {

    // Identity fields
    private Name name;
    private final ModuleCode moduleCode;
    private int mcCount;
    private Color color;
    private boolean prereqsSatisfied;

    // Data fields
    private PrereqTree prereqTree;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Module(Name name, ModuleCode moduleCode, int mcCount, Color color, PrereqTree prereqTree,
                  UniqueTagList tags) {
        requireAllNonNull(name, moduleCode, mcCount, color, tags);
        this.name = name;
        this.moduleCode = moduleCode;
        this.mcCount = mcCount;
        this.color = color;
        this.prereqsSatisfied = false;
        this.prereqTree = prereqTree;
        this.tags = tags;
    }

    /**
     * This constructor is used for {@code JsonAdaptedSkeletalModule} to create a skeletal module object from
     * JSON file. This skeletal module object is temporary and will be replace by the actual {@code Module}
     * once its study plan gets activated.
     */
    public Module(ModuleCode moduleCode) {
        requireAllNonNull(moduleCode);
        this.moduleCode = moduleCode;
    }

    /**
     * This constructor is used for {@code JsonAdaptedModule} to create an incomplete module object from
     * JSON file. This incomplete module object resides in the "Mega module list" of a study plan. Its data
     * fields will be filled from {@code ModuleInfo} once its study plan gets activated.
     */
    public Module(ModuleCode moduleCode, Color color, List<Tag> tags) {
        requireAllNonNull(moduleCode, color, tags);
        this.moduleCode = moduleCode;
        this.color = color;
        this.tags = new UniqueTagList();
        this.tags.setTags(tags);
    }

    public Name getName() {
        return name;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public int getMcCount() {
        return mcCount;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setMcCount(int mcCount) {
        this.mcCount = mcCount;
    }

    /**
     * Adds the specified tag to the module if it is not already there.
     *
     * @param tag Tag to be attached to the module.
     * @return True if the tag has been added and false otherwise.
     */
    public boolean addTag(Tag tag) {
        if (hasTag(tag)) {
            return false;
        }
        tags.addTag(tag);
        return true;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean getPrereqsSatisfied() {
        return this.prereqsSatisfied;
    }

    public String getPrereqString() {
        return (this.prereqTree == null) ? "" : this.prereqTree.toString();
    }

    /**
     * Deletes the specified user tag to the module if the module has it.
     *
     * @param userTag user tag to be deleted from the module.
     * @return True if the user tag has been deleted and false otherwise.
     */
    public boolean deleteUserTag(UserTag userTag) {
        if (!hasTag(userTag)) {
            return false;
        }
        tags.remove(userTag);
        return true;
    }

    private boolean hasTag(Tag tag) {
        return tags.contains(tag);
    }

    /**
     * Returns a unique tag list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public UniqueTagList getTags() {
        return tags;
    }

    /**
     * This method verifies previous semester codes against the prerequisite tree, and updates its
     * {@code prereqsSatisfied} property accordingly.
     *
     * @param prevSemCodes Codes of modules taken in previous semesters
     */
    public void verify(List<String> prevSemCodes) {
        if (this.prereqTree == null) {
            this.prereqsSatisfied = true;
        } else {
            this.prereqsSatisfied = this.prereqTree.verify(prevSemCodes);
        }
    }

    /**
                * Returns true if both modules have the same identity and data fields.
                */
        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof seedu.address.model.module.Module)) {
                return false;
            }

            return this.moduleCode.equals(((Module) other).moduleCode);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, moduleCode, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Module code: ")
                .append(getModuleCode())
                .append(" MCs: ")
                .append(getMcCount())
                .append(" Prereqs satisfied: ")
                .append(getPrereqsSatisfied())
                .append(" Tags: ");
        if (getTags() != null) {
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }

    @Override
    public Module clone() throws CloneNotSupportedException {
        Module clone = (Module) super.clone();
        clone.tags = (UniqueTagList) tags.clone();

        return clone;
    }
}
