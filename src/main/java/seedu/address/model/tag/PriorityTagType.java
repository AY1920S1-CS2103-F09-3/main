package seedu.address.model.tag;

/**
 * Represents priority tag types.
 */
public enum PriorityTagType {
    HIGH("HIGH", "highPriority"),
    MEDIUM("MEDIUM", "mediumPriority"),
    LOW("LOW", "lowPriority");

    private String priorityTagTypeName;
    private String style;

    PriorityTagType(String priorityTagTypeName, String style) {
        this.priorityTagTypeName = priorityTagTypeName;
        this.style = style;
    }

    public static boolean isValidPriorityTagString(String s) {
        String capitalised = s.toUpperCase();
        return capitalised.equals("HIGH") || capitalised.equals("MEDIUM") || capitalised.equals("LOW");
    }

    public String getPriorityTagTypeName() {
        return priorityTagTypeName;
    }

    public String getStyle() {
        return style;
    }
}
