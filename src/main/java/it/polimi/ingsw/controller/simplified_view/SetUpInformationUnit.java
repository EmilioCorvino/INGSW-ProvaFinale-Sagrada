package it.polimi.ingsw.controller.simplified_view;

import it.polimi.ingsw.model.Color;

/**
 * This class manages the information to send to the client for setting up its elements.
 */
public class SetUpInformationUnit extends InformationUnit {

    /**
     * The color of the die to set.
     */
    private Color color;

    /**
     * The value of the die to set.
     */
    private int value;

    /**
     * Default constructor.
     */
    public SetUpInformationUnit() {

    }

    public SetUpInformationUnit(int index, Color color, int value) {
        super(index);
        setColor(color);
        setValue(value);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}