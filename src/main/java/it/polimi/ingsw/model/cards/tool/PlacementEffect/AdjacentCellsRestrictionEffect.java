package it.polimi.ingsw.model.cards.tool.PlacementEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.player.Player;

/**
 * This class manages the tool card effect that allows the player to place a die in a cell that is not adjacent
 * to a non empty cell. If the desired cell is adjacent to a non empty cell the placement has to respect all the
 * default restriction.
 */
public class AdjacentCellsRestrictionEffect extends PlacementRestrictionEffect {

    /**
     * This method manages the particular placement ignoring the adjacent cells restriction.
     * @param manager the controller.
     * @param info the info unit to use.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit info) {

        Player p = manager.getControllerMaster().getGameState().getCurrentPlayer();
        WindowPatternCard wp = p.getWindowPatternCard();
        wp.createCopy();
        Die chosenDie = wp.removeDie(info.getSourceIndex());

        Cell desiredCell = new Cell(info.getSourceIndex() / WindowPatternCard.getMaxCol(), info.getSourceIndex() % WindowPatternCard.getMaxCol());
        wp.setDesiredCell(desiredCell);

        Cell[][] gwCopy = wp.getGlassWindowCopy();

        if(!wp.checkAdjacentCells(desiredCell, gwCopy)) {
            if(wp.checkOwnRuleSet(chosenDie, desiredCell, gwCopy)) {
                manager.setMoveLegal(true);
                wp.addDie(wp.removeDie(info.getSourceIndex()));
                manager.showRearrangementResult(p, info);
                return;
            } else {
                manager.sendNotificationToCurrentPlayer(wp.getErrorMessage() + "digita aiuto per vedere i tuoi comandi");
                manager.setMoveLegal(false);
                return;
            }
        }

        super.executeMove(manager, info);
    }
}
