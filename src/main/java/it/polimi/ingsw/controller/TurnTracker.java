package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.ToolCardSlot;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 *
 */
public class TurnTracker {

    /**
     *
     */
    private boolean diePlaced;

    /**
     *
     */
    private boolean toolCardUsed;

    public TurnTracker() {
        this.diePlaced = false;
        this.toolCardUsed = false;
    }


    public boolean existToolCardToUse(List<ToolCardSlot> toolsToCheck, Player currPlayer) {
        toolsToCheck.forEach( tool -> {


        });

        return false;
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

}
