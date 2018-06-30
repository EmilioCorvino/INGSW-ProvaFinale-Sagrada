package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

/**
 * This class manages the
 */
public class DiePlacementMove implements IMove {

    /**
     * This method performs the placement move in the window pattern card.
     * @param manager the manager.
     * @param setUpInfoUnit the information container in which the coordinates to use are stored.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();

        Cell desiredCell = new Cell(setUpInfoUnit.getDestinationIndex() / WindowPatternCard.getMaxCol(), setUpInfoUnit.getDestinationIndex() % WindowPatternCard.getMaxCol());
        wp.setDesiredCell(desiredCell);

        wp.createCopy();
        manager.getControllerMaster().getCommonBoard().getDraftPool().createCopy();

        Die die = manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice().get(setUpInfoUnit.getSourceIndex());

        if(!wp.canBePlaced(die, desiredCell, wp.getGlassWindowCopy())) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer(wp.getErrorMessage() + "\nDigita 'comandi' per visualizzare nuovamente i tuoi comandi.");
            return;
        }

        manager.setMoveLegal(true);

        //Generation of SetUpInformationUnits to send to the view.
        //this goes in a proper method and show placement result takes one parameter as input.
        SetUpInformationUnit wpSetUpInfoUnit = new SetUpInformationUnit();
        wpSetUpInfoUnit.setColor(die.getDieColor());
        wpSetUpInfoUnit.setValue(die.getActualDieValue());
        wpSetUpInfoUnit.setDestinationIndex(setUpInfoUnit.getDestinationIndex());
        wpSetUpInfoUnit.setSourceIndex(setUpInfoUnit.getSourceIndex());

        manager.setMoveLegal(true);

        //CAREFUL
        DiceDraftPool draft = manager.getControllerMaster().getCommonBoard().getDraftPool();
        draft.createCopy();
        Die dieToRemove = draft.removeDie(setUpInfoUnit.getSourceIndex());
        wp.addDie(dieToRemove);
        manager.showPlacementResult(manager.getControllerMaster().getGameState().getCurrentPlayer(), wpSetUpInfoUnit);
    }
}