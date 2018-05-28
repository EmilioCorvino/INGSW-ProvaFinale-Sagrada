package it.polimi.ingsw.controller.simplified_view;

/**
 * This class manages the information to send to the client for setting up its elements.
 */
public class SetUpInformationUnit extends InformationUnit {

    /**
     * The color of the die to set.
     */
    private String color;

    /**
     * The value of the die to set.
     */
    private int value;

    public SetUpInformationUnit(int index, String color, int value) {
        super(index);
        setColor(color);
        setValue(value);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}