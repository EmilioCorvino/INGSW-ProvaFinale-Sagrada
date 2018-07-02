package it.polimi.ingsw.model.cards.tool.effects.ignore;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.move.DefaultDiePlacementMove;
import it.polimi.ingsw.model.player.Player;

/**
 * This class manages the tool card effect that allows the player to place a die in a cell that is not adjacent
 * to a non empty cell. If the desired cell is adjacent to a non empty cell the placement has to respect all the
 * default restriction.
 */
public class IgnoreAdjacentCellsRestrictionEffect extends DefaultDiePlacementMove {

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

        DiceDraftPool draft = manager.getControllerMaster().getCommonBoard().getDraftPool();
        draft.createCopy();
        Die chosenDie = draft.getAvailableDiceCopy().get(info.getSourceIndex());

        Cell desiredCell = new Cell(info.getDestinationIndex() / WindowPatternCard.getMaxCol(), info.getDestinationIndex() % WindowPatternCard.getMaxCol());
        Cell[][] gwCopy = wp.getGlassWindowCopy();

        if (!wp.matrixIsEmpty(gwCopy) && !wp.checkAdjacentCells(desiredCell, gwCopy)) {
            if (wp.checkOwnRuleSet(chosenDie, desiredCell, gwCopy)) {
                manager.setMoveLegal(true);
                wp.setDesiredCell(desiredCell);
                wp.addDieToCopy(draft.removeDieFromCopy(info.getSourceIndex()));
                info.setValue(chosenDie.getActualDieValue());
                info.setColor(chosenDie.getDieColor());
                manager.showPlacementResult(p, info);
                return;
            } else {
                manager.sendNotificationToCurrentPlayer(wp.getErrorMessage() + " Digita 'Comandi' per vedere i tuoi comandi");
                manager.setMoveLegal(false);
                return;
            }
        }

        super.executeMove(manager, info);

        //todo check if this has to use defaultPlacement or AValue effect placement. Remember to increment the die placed count.
    }
}
