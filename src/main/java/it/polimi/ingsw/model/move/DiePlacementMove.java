package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.player.Player;

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

        Player p = manager.getControllerMaster().getGameState().getCurrentPlayer();
        WindowPatternCard wp = p.getWindowPatternCard();

        Die die = manager.getControllerMaster().getCommonBoard().getDraftPool().removeDie(setUpInfoUnit.getSourceIndex());

        if(!checkPlacement(wp, die, manager, setUpInfoUnit))
            return;

        //Generation of SetUpInformationUnits to send to the view.
        SetUpInformationUnit wpSetUpInfoUnit = new SetUpInformationUnit();
        wpSetUpInfoUnit.setColor(die.getDieColor());
        wpSetUpInfoUnit.setValue(die.getActualDieValue());
        wpSetUpInfoUnit.setDestinationIndex(setUpInfoUnit.getDestinationIndex());

        SetUpInformationUnit draftSetUpInfoUnit = new SetUpInformationUnit();
        draftSetUpInfoUnit.setDestinationIndex(setUpInfoUnit.getSourceIndex());

        //CAREFUL
        wp.addDie(die);
        manager.showPlacementResult(p, wpSetUpInfoUnit, draftSetUpInfoUnit);
    }

    /**
     *
     * @param wp
     * @param chosenDie
     * @param manager
     * @param info
     * @return
     */
    public boolean checkPlacement(WindowPatternCard wp, Die chosenDie, GamePlayManager manager, SetUpInformationUnit info) {
        Cell desiredCell = new Cell(info.getDestinationIndex() / WindowPatternCard.getMaxCol(), info.getDestinationIndex() % WindowPatternCard.getMaxCol());
        wp.setDesiredCell(desiredCell);

        if (!wp.canBePlaced(chosenDie, desiredCell)) {
            if(manager.getControllerMaster().getGameState().getActualRound()== 1) {
                manager.showNotification("Il dado deve essere piazzato sui bordi o in uno degli angoli");
                return false;
            }
            else {
                manager.showNotification("Il dado non può essere piazzato perchè non rispetta le restrizioni");
                return false;
            }
        }
        return true;
    }
}