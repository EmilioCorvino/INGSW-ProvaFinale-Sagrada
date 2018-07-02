package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages a particular type of move: the restricted move, which has to be performed under certain
 * conditions and when one or more of them fall some specific actions need to be performed.
 */
public class RestrictedDiePlacementMove extends AMove {

    /**
     * This method executed the move considering some specific conditions.
     * @param manager the controller.
     * @param setUpInfoUnit the info to consider.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        Player player = manager.getControllerMaster().getGameState().getCurrentPlayer();
        WindowPatternCard wp = player.getWindowPatternCard();
        wp.createCopy();
        Die dieToCheck = new Die(setUpInfoUnit.getValue(), setUpInfoUnit.getColor());
        Cell cell = new Cell(setUpInfoUnit.getDestinationIndex() / WindowPatternCard.getMaxCol(), setUpInfoUnit.getDestinationIndex() % WindowPatternCard.getMaxCol());


        if(wp.canBePlaced(dieToCheck, cell, wp.getGlassWindowCopy())) {
            wp.setDesiredCell(cell);
            wp.addDieToCopy(dieToCheck);
            manager.setMoveLegal(true);
            manager.showPlacementResult(player, setUpInfoUnit);
        } else {
            if(!checkExistingCellsToUse(wp, dieToCheck)) {
                manager.setMoveLegal(true);
                manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDiceCopy().add(dieToCheck);
                manager.getControllerMaster().getGameState().getCurrentTurn().setDiePlaced(false);
                manager.showUpdatedDraft(packMultipleInformation(manager));
            } else {
                manager.setMoveLegal(false);
            }
        }
    }

    /**
     * This method checks if exists at least one cell in which the user can place a die.
     * @param wp the window pattern of the player.
     * @param chosenDie the die to place.
     * @return true if exists at least one cell, false otherwise.
     */
    private boolean checkExistingCellsToUse(WindowPatternCard wp, Die chosenDie) {
        Cell[][] gwCopy = wp.getGlassWindowCopy();
        for(int i=0; i< WindowPatternCard.getMaxRow(); i++)
            for (int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if (wp.canBePlaced(chosenDie, gwCopy[i][j], gwCopy))
                    return true;
            }
        return false;
    }

    /**
     * This method packs multiple information - results to send to the controller.
     * @param manager the controller.
     * @return a list of results.
     */
    private List<SetUpInformationUnit> packMultipleInformation(GamePlayManager manager) {
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
