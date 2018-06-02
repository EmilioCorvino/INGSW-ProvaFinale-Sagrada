package it.polimi.ingsw.controller.simplified_view;

import java.io.Serializable;

/**
 * This class manages the basic info the client has to set to tell the controller how to perform the move.
 */
public class InformationUnit implements Serializable {

    /**
     * The index inside the specific container chosen by the player.
     */
    private int index;

    /**
     * The specific offset containing the specific die, if needed.
     */
    private int offset;

    /**
     * Default constructor.
     */
    public InformationUnit() {

    }

    public InformationUnit(int index) {
        //0 is the default value, but it can be changed if needed.
        setOffset(0);
        setIndex(index);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}