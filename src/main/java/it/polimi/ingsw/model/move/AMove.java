package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;

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
}
