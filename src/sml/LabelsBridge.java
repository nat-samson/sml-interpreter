package sml;

/**
 * This class provides partial access to members of Labels class outside the package
 */
public class LabelsBridge {
    Labels labels;

    /**
     * Constructor: Stores reference to Labels object
     *
     * @param l Labels in use by current Machine
     */
    public LabelsBridge(Labels l) {
        labels = l;
    }

    /**
     * Call Labels' indexOf method
     *
     * @param label the name of a given label
     * @return the index of the given label in the program
     */
    public int indexOf(String label) {
        return labels.indexOf(label);
    }

    /**
     * Call Labels' addLabel method
     *
     * @param label a new label name
     */
    public void addLabel(String label) {
        labels.addLabel(label);
    }
}