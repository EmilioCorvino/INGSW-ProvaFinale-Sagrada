package it.polimi.ingsw.server.model.move;

import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.server.controller.managers.GamePlayManager;
import it.polimi.ingsw.server.model.die.Cell;
import it.polimi.ingsw.server.model.die.Die;
import it.polimi.ingsw.server.model.die.containers.WindowPatternCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This interface represents a generic move a player can perform
 * @see DefaultDiePlacementMove
 * @see RestrictedDiePlacementMove
 * @see it.polimi.ingsw.server.model.cards.tool.effects.AToolCardEffect
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
        String messageToRestore = wp.getErrorMessage();
        Cell[][] gwCopy = wp.getGlassWindowCopy();
        for(int i=0; i< WindowPatternCard.getMaxRow(); i++) {
            for (int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if (wp.canBePlaced(chosenDie, gwCopy[i][j], gwCopy)) {
                    wp.setErrorMessage(messageToRestore);
                    return true;
                }
            }
        }
        wp.setErrorMessage(messageToRestore);
        return false;
    }

    /**
     * This method packs multiple information - results to send to the controller.
     * @param manager part of the controller that deals with the game play.
     * @return a list of {@link SetUpInformationUnit} to send back.
     */
    protected List<SetUpInformationUnit> packMultipleInformation(GamePlayManager manager) {
        List<Die> list = manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDiceCopy();
        List<SetUpInformationUnit> listToSend = new ArrayList<>();
        for (Die d : list) {
            SetUpInformationUnit setup = new SetUpInformationUnit();
            setup.setSourceIndex(list.indexOf(d));
            setup.setValue(d.getActualDieValue());
            setup.setColor(d.getDieColor());
            listToSend.add(setup);
        }
        return listToSend;
    }
}
