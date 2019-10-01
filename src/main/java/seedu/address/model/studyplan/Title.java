package seedu.address.model.studyplan;

public class Title implements Cloneable{
    private String value;

    public Title(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public Title clone() throws CloneNotSupportedException {
        return (Title) super.clone();
    }
}
