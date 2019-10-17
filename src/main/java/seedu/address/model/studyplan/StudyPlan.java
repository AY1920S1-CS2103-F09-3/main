package seedu.address.model.studyplan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import seedu.address.model.Color;
import seedu.address.model.ModuleInfo;
import seedu.address.model.ModulesInfo;
import seedu.address.model.PrereqTree;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Name;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.semester.exceptions.SemesterAlreadyBlockedException;
import seedu.address.model.semester.exceptions.SemesterNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a study plan in the module planner.
 */
public class StudyPlan implements Cloneable {

    private static int totalNumberOfStudyPlans = 0;

    private UniqueSemesterList semesters;
    private Title title;
    private int index; // unique identifier of this study plan
    private SemesterName currentSemester;

    // the "Mega-List" of modules of this study plan. All modules in an *active* study plan refer to a module here.
    // note: this Mega-List is only constructed when a study plan gets activated.
    private HashMap<String, Module> modules;

    // the unique list of tags of this study plan.
    // All tags in an *active* study plan refer to a tag here.
    // note: this unique list of tags is only constructed when a study plan gets activated.
    private UniqueTagList tags;


    // to create a study plan without a Title
    public StudyPlan(ModulesInfo modulesInfo, SemesterName currentSemester) {
        this(new Title(""), modulesInfo, currentSemester);
    }

    // to create a study plan with a Title
    public StudyPlan(Title title, ModulesInfo modulesInfo, SemesterName currentSemester) {
        this.title = title;
        this.semesters = new UniqueSemesterList();
        this.currentSemester = currentSemester;
        setDefaultSemesters();

        // switch the current active plan to the newly created one. Reason: user can directly add modules to it.

        tags = new UniqueTagList();
        tags.initDefaultTags();

        setMegaModuleHashMap(modulesInfo);

        totalNumberOfStudyPlans++;
        this.index = totalNumberOfStudyPlans;
    }

    /**
     * This constructor is used for {@code JsonAdaptedStudyPlan}.
     */
    public StudyPlan(Title modelTitle, int modelIndex, List<Semester> modelSemesters,
                     HashMap<String, Module> modelModules, List<Tag> modelTags, SemesterName currentSemester) {
        this.title = modelTitle;
        this.index = modelIndex;
        this.semesters = new UniqueSemesterList();
        this.semesters.setSemesters(modelSemesters);
        this.modules = modelModules;
        this.tags = new UniqueTagList();
        tags.setTags(modelTags);
        this.currentSemester = currentSemester;

    }

    // make a copy of the current study without incrementing the index, for version tracking commits
    public StudyPlan copyForCommit() throws CloneNotSupportedException {
        return this.clone();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Title getTitle() {
        return title;
    }

    public UniqueSemesterList getSemesters() {
        return semesters;
    }

    public int getIndex() {
        return index;
    }

    // "Mega-list" of modules
    public HashMap<String, Module> getModules() {
        return modules;
    }

    // "Mega-list" of tags
    public UniqueTagList getTags() {
        return tags;
    }

    public SemesterName getCurrentSemester() {
        return currentSemester;
    }

    public static int getTotalNumberOfStudyPlans() {
        return totalNumberOfStudyPlans;
    }

    public static void setTotalNumberOfStudyPlans(int totalNumberOfStudyPlans) {
        StudyPlan.totalNumberOfStudyPlans = totalNumberOfStudyPlans;
    }

    /**
     * Populates the unique semester list with the 8 semesters in the normal 4-year candidature. These
     * semesters will be empty initially.
     */
    public void setDefaultSemesters() {
        semesters.add(new Semester(SemesterName.Y1S1));
        semesters.add(new Semester(SemesterName.Y1S2));
        semesters.add(new Semester(SemesterName.Y2S1));
        semesters.add(new Semester(SemesterName.Y2S2));
        semesters.add(new Semester(SemesterName.Y3S1));
        semesters.add(new Semester(SemesterName.Y3S2));
        semesters.add(new Semester(SemesterName.Y4S1));
        semesters.add(new Semester(SemesterName.Y4S2));
    }

    /**
     * Given a {@code ModuleInfo} object, convert it to a {@code Module}.
     */
    private Module convertModuleInfoToModule(ModuleInfo moduleInfo) {
        // TODO: Yi Wai: assign default tags to the result (Module).
        UniqueTagList moduleTagList = assignDefaultTags(moduleInfo);
        Name name = new Name(moduleInfo.getName());
        ModuleCode moduleCode = new ModuleCode(moduleInfo.getCode());
        int mcCount = moduleInfo.getMc();
        PrereqTree prereqTree = moduleInfo.getPrereqTree();
        return new Module(name, moduleCode, mcCount, Color.RED, prereqTree, moduleTagList);
    }

    /**
     * Returns a {@code UniqueTagList} with the default tags attached to the module with the given module info.
     *
     * @param moduleInfo The module info of the module.
     * @return A {@code UniqueTagList} with the default tags.
     */
    private UniqueTagList assignDefaultTags(ModuleInfo moduleInfo) {
        UniqueTagList moduleTagList = new UniqueTagList();
        UniqueTagList studyPlanTagList = getTags();
        // assign focus primary tags
        List<String> focusPrimaries = moduleInfo.getFocusPrimaries();
        for (String focusPrimary : focusPrimaries) {
            moduleTagList.addTag(studyPlanTagList.getDefaultTag(focusPrimary + ":P"));
        }
        // assign focus elective tags
        List<String> focusElectives = moduleInfo.getFocusElectives();
        for (String focusElective : focusElectives) {
            moduleTagList.addTag(studyPlanTagList.getDefaultTag(focusElective + ":E"));
        }
        // assign s/u-able tag
        boolean canSu = moduleInfo.getSuEligibility();
        if (canSu) {
            moduleTagList.addTag(studyPlanTagList.getDefaultTag("S/U-able"));
        }
        // assign completed tag
        for (Semester semester : semesters) {
            UniqueModuleList uniqueModuleList = semester.getModules();
            for (Module module : uniqueModuleList) {
                if (module.getName().toString().equals(moduleInfo.getName())) {
                    if (semester.getSemesterName().compareTo(currentSemester) < 0) {
                        moduleTagList.addTag(studyPlanTagList.getDefaultTag("Completed"));
                    }
                    break;
                }
            }
        }
        // assign core tag
        boolean isCore = moduleInfo.getIsCore();
        if (isCore) {
            moduleTagList.addTag(studyPlanTagList.getDefaultTag("Core"));
        }

        //TODO add ue?, ulr? tags

        return moduleTagList;
    }

    private void setMegaModuleHashMap(ModulesInfo modulesInfo) {
        HashMap<String, ModuleInfo> moduleInfoHashMap = modulesInfo.getModuleInfoHashMap();
        modules = new HashMap<>();
        for (ModuleInfo moduleInfo : moduleInfoHashMap.values()) {
            Module module = convertModuleInfoToModule(moduleInfo);
            modules.put(module.getModuleCode().toString(), module);
        }
    }

    /**
     * Adds a module to a semester, given the {@code ModuleCode} and {@code SemesterName}.
     *
     * @param moduleCode   module code of the module to be added.
     * @param semesterName semester name of the target semester.
     * @throws SemesterNotFoundException
     */
    public void addModuleToSemester(ModuleCode moduleCode, SemesterName semesterName)
            throws SemesterNotFoundException {
        Semester targetSemester = null;
        for (Semester semester : semesters) {
            if (semester.getSemesterName().equals(semesterName)) {
                targetSemester = semester;
                break;
            }
        }
        if (targetSemester == null) {
            throw new SemesterNotFoundException();
        }

        Module moduleToAdd = modules.get(moduleCode.toString());
        targetSemester.addModule(moduleToAdd);
    }

    /**
     * Blocks a semester with the given {@code SemesterName} so that the user cannot add modules to that semester.
     * The user can enter a reason for blocking it (e.g. NOC, internship).
     */
    public void blockSemester(SemesterName semesterName, String reasonForBlock) throws SemesterNotFoundException {
        Semester semesterToBlock = null;
        Iterator<Semester> iterator = semesters.iterator();
        while (iterator.hasNext()) {
            Semester semester = iterator.next();
            if (semester.getSemesterName().equals(semesterName)) {
                semesterToBlock = semester;
            }
        }
        if (semesterToBlock == null) {
            throw new SemesterNotFoundException();
        }
        if (semesterToBlock.isBlocked()) {
            throw new SemesterAlreadyBlockedException();
        }
        semesterToBlock.setBlocked(true);
        semesterToBlock.setReasonForBlocked(reasonForBlock);
    }

    /**
     * Returns true if both study plans of the same index have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two study plans.
     */
    public boolean isSameStudyPlan(StudyPlan other) {
        return this.index == other.index;
    }

    /**
     * Updates the prerequisites of all modules in the study plan.
     * This method should be called every time there is a change in its modules.
     */
    public void updatePrereqs() {
        ArrayList<String> prevSemCodes = new ArrayList<>();
        for (Semester sem : semesters) {
            ArrayList<String> thisSemCodes = new ArrayList<>();
            for (Module mod : sem.getModules()) {
                String moduleCode = mod.getModuleCode().toString();
                thisSemCodes.add(moduleCode);
                mod.verify(prevSemCodes);
            }
            prevSemCodes.addAll(thisSemCodes);
        }
    }

    /**
     * Returns a copy of the current study plan.
     *
     * @return a clone of this study plan.
     * @throws CloneNotSupportedException
     */
    public StudyPlan clone() throws CloneNotSupportedException {
        StudyPlan clone = (StudyPlan) super.clone();
        clone.semesters = semesters.clone();
        clone.title = title.clone();
        clone.index = index;

        // because of this, the mega-lists fields don't have final keyword
        clone.modules = new HashMap<>();
        for (Semester semester: clone.semesters) {
            for (Module module: semester.getModules()) {
                clone.modules.put(module.getModuleCode().toString(), module);
            }
        }
        for (Module module : modules.values()) {
            if (!clone.modules.containsValue(module)) {
                clone.modules.put(module.getModuleCode().toString(), module.clone());
            }
        }

        clone.tags = (UniqueTagList) tags.clone();

        return clone;
    }

    @Override
    // TODO: this currently compares only the index. Does this need to be modified?
    public boolean equals(Object other) {
        if (other instanceof StudyPlan) {
            return this.index == ((StudyPlan) other).index;
            //&& this.semesters.equals(((StudyPlan) other).getSemesters());
        } else {
            return false;
        }
    }
}
