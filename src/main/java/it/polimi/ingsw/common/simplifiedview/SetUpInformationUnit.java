package it.polimi.ingsw.common.simplifiedview;

import it.polimi.ingsw.common.Color;

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

    /**
     * This constructor builds a SetUpInformationUnit with some parameters set.
     * @param index index of the desired destination in which the die has to be put.
     * @param color color of the die to place.
     * @param value value of the die to place.
     */
    public SetUpInformationUnit(int index, Color color, int value) {
        setDestinationIndex(index);
        setColor(color);
        setValue(value);
    }

    /**
     * This constructor builds an empty SetUpInformationUnit.
     */
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

    /**
     * This method is used if there is the need to restore the result of a move gone wrong.
     * @return a new {@link SetUpInformationUnit} equal to this, except for the fact it has inverted source and
     * destination.
     */
    public SetUpInformationUnit invertSourceAndDestination() {
        int restoreDestination = this.getSourceIndex();
        int restoreSource = this.getDestinationIndex();
        SetUpInformationUnit restoreSetUpInfoUnit = new SetUpInformationUnit();
        restoreSetUpInfoUnit.setDestinationIndex(restoreDestination);
        restoreSetUpInfoUnit.setSourceIndex(restoreSource);
        restoreSetUpInfoUnit.setValue(this.getValue());
        restoreSetUpInfoUnit.setColor(this.getColor());
        return restoreSetUpInfoUnit;
    }
}