package it.polimi.ingsw.model.turn;

import it.polimi.ingsw.model.player.Player;

/**
 * This class represents the state of the turns of a match.
 */
public class Turn {

    /**
     * This attribute represents the player on duty.
     */
    private Player player;

    /**
     * This attribute indicates if the current player has placed a die.
     */
    private boolean diePlaced;

    /**
     * This attribute is the counter of how many dice have been placed within the turn.
     */
    private int dieCount;

    /**
     * This attribute indicates if the current player has used a tool card.
     */
    private boolean toolCardUsed;

    /**
     * This attribute indicates if the player has willingly ended his turn.
     */
    private boolean passed;

    /**
     * This attribute indicates the id of the slot containing the tool card the player has used.
     * -1 is flag value: the indexes should go from 0 to 3.
     */
    private int toolSlotUsed = -1;

    Turn(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isTurnCompleted() {
        return ((this.diePlaced && this.toolCardUsed) || this.passed);
    }

    public boolean isDiePlaced() {
        return diePlaced;
    }

    public void setDiePlaced(boolean diePlaced) {
        this.diePlaced = diePlaced;
    }

    public int getDieCount() {
        return this.dieCount;
    }

    public void incrementDieCount() {
        this.dieCount++;
    }

    public boolean isToolCardUsed() {
        return toolCardUsed;
    }

    public void setToolCardUsed(boolean toolCardUsed) {
        this.toolCardUsed = toolCardUsed;
    }

    public int getToolSlotUsed() {
        return toolSlotUsed;
    }

    public void setToolSlotUsed(int toolSlotUsed) {
        this.toolSlotUsed = toolSlotUsed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}