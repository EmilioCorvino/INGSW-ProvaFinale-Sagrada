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
     * This attribute indicates if the current player has used a tool card.
     */
    private boolean toolCardUsed;

    /**
     * This attribute indicates if the player has willingly ended his turn.
     */
    private boolean passed;

    /**
     * This attribute indicates the id of the slot containing the tool card the player has used.
     */
    private int toolSlotUsed;

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
}