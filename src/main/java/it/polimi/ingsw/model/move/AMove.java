package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

/**
 * This interface represents a generic move a player can perform
 * @see DefaultDiePlacementMove
 * @see RestrictedDiePlacementMove
 * @see it.polimi.ingsw.model.cards.tool.effects.AToolCardEffect
 */
public abstract class AMove {

    protected static final String COMMANDS_HELP = " Digita 'comandi' per visualizzare i comandi ancora disponibili.";

    /**
     * This method is the one that effectively performs the move. It can be used for a default move or a card
     * move.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInfoUnit object containing all the information needed to perform the move.
     */
    public abstract void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit);


    /**
     * This method checks if exists at least one cell in which the user can place a die.
     * @param wp the window pattern of the player.
     * @param chosenDie the die to place.
     * @return true if exists at least one cell, false otherwise.
     */
    public boolean checkExistingCellsToUse(WindowPatternCard wp, Die chosenDie) {
        Cell[][] gwCopy = wp.getGlassWindowCopy();
        for(int i=0; i< WindowPatternCard.getMaxRow(); i++)
            for (int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if (wp.canBePlaced(chosenDie, gwCopy[i][j], gwCopy))
                    return true;
            }
        return false;
    }
}
