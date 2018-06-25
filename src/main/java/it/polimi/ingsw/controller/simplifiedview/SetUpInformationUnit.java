package it.polimi.ingsw.controller.simplifiedview;

import it.polimi.ingsw.model.Color;

import java.io.Serializable;

/**
 * This class manages the information to send to the client for setting up its elements and the information
 * to use to update the view.
 */
public class SetUpInformationUnit implements Serializable {

    /**
     * The color of the die to set.
     */
    private Color color;

    /**
     * The value of the die to set.
     */
    private int value;

    /**
     * The index inside the source container.
     */
    private int sourceIndex;

    /**
     * The offset of the index inside a container.
     */
    private int offset;

    /**
     * The index inside the destination container.
     */
    private int destinationIndex;

    /**
     * Extra params to set for example for some effects of the tool cards.
     */
    private int extraParam;

    public SetUpInformationUnit(int index, Color color, int value) {
        setDestinationIndex(index);
        setColor(color);
        setValue(value);
    }

    public SetUpInformationUnit() {
        setColor(Color.BLANK);
        setValue(0);
        setSourceIndex(0);
        setDestinationIndex(0);
        setOffset(0);
        setExtraParam(0);
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

    public int getSourceIndex() {
        return sourceIndex;
    }

    public void setSourceIndex(int sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getDestinationIndex() {
        return destinationIndex;
    }

    public void setDestinationIndex(int destinationIndex) {
        this.destinationIndex = destinationIndex;
    }

    public int getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(int extraParam) {
        this.extraParam = extraParam;
    }
}