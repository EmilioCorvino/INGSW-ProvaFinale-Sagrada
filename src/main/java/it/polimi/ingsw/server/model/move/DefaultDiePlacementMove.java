package it.polimi.ingsw.server.model.move;

import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.server.controller.managers.GamePlayManager;
import it.polimi.ingsw.server.model.die.Cell;
import it.polimi.ingsw.server.model.die.Die;
import it.polimi.ingsw.server.model.die.containers.WindowPatternCard;

/**
 * This class manages the
 */
public class DefaultDiePlacementMove extends AMove {

    /**
     * This method performs the placement move in the window pattern card.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInfoUnit the information container in which the coordinates to use are stored.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        if (manager.getEffectCounter() == 0) {
            manager.incrementEffectCounter();
        }

        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();

        Cell desiredCell = new Cell(setUpInfoUnit.getDestinationIndex() / WindowPatternCard.getMaxCol(), setUpInfoUnit.getDestinationIndex() % WindowPatternCard.getMaxCol());

        wp.createCopy();
        manager.getControllerMaster().getCommonBoard().getDraftPool().createCopy();

        Die die = manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDiceCopy().get(setUpInfoUnit.getSourceIndex());

        if(!wp.canBePlaced(die, desiredCell, wp.getGlassWindowCopy())) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer(wp.getErrorMessage() + COMMANDS_HELP);
            return;
        }

        //Generation of SetUpInformationUnits to send to the view.
        //this goes in a proper method and show placement result takes one parameter as input.
        SetUpInformationUnit wpSetUpInfoUnit = new SetUpInformationUnit();
        wpSetUpInfoUnit.setColor(die.getDieColor());
        wpSetUpInfoUnit.setValue(die.getActualDieValue());
        wpSetUpInfoUnit.setDestinationIndex(setUpInfoUnit.getDestinationIndex());
        wpSetUpInfoUnit.setSourceIndex(setUpInfoUnit.getSourceIndex());

        manager.setMoveLegal(true);
        manager.getControllerMaster().getGameState().getCurrentTurn().incrementDieCount();

        //Update of model and view.
        Die dieToRemove = manager.getControllerMaster().getCommonBoard().getDraftPool().removeDieFromCopy(setUpInfoUnit.getSourceIndex());
        wp.setDesiredCell(desiredCell);
        wp.addDieToCopy(dieToRemove);
        manager.showPlacementResult(manager.getControllerMaster().getGameState().getCurrentPlayer(), wpSetUpInfoUnit);
    }
}