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
     *
     */
    private int sourceIndex;

    /**
     *
     */
    private int sourceOffset;

    /**
     * Default constructor.
     */
    public SetUpInformationUnit() {

    }

    public SetUpInformationUnit(int index, Color color, int value) {
        super(index);
        setColor(color);
        setValue(value);
        setSourceIndex(0);
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

    public void setIndex(int index) {
        super.setIndex(index);
    }

    public int getIndex() {
        return super.getIndex();
    }

    public int getSourceIndex() {
        return sourceIndex;
    }

    public void setSourceIndex(int sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public int getSourceOffset() {
        return sourceOffset;
    }

    public void setSourceOffset(int sourceOffset) {
        this.sourceOffset = sourceOffset;
    }
}