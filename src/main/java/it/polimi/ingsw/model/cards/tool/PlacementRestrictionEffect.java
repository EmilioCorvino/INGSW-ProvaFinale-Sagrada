package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.controller.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.move.DiePlacementMove;

/**
 *
 */
public class PlacementRestrictionEffect extends AToolCardEffect {

    /**
     * This method executes the specific effect of the tool that allows the user to move a die from a cell to another
     * of the personal window pattern card.
     * @param manager the controller.
     * @param setUpInfoUnit the unit information to use to perform the effect.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {
        Die chosenDie = new Die(setUpInfoUnit.getValue(), setUpInfoUnit.getColor());
        WindowPatternCard playerWp = manager.getControllerMaster().getGameState().getActualPlayer().getWindowPatternCard();

        if(checkContainedDie(playerWp, chosenDie, manager)) {
            playerWp.removeDie(chosenDie);
            DiePlacementMove move = new DiePlacementMove();
            move.executeMove(manager, setUpInfoUnit);
        }
    }


    /**
     * This method is called to check if the window pattern card of a player contains the die.
     * @param windowPatternCard the window to analyze.
     * @param chosenDie the die to check.
     * @param manager the controller manager.
     * @return true if it is contained, false otherwise.
     */
    public boolean checkContainedDie(WindowPatternCard windowPatternCard, Die chosenDie, GamePlayManager manager) {

        if(!windowPatternCard.isContained(chosenDie)) {
            manager.showNotification("il dado non è contenuto in in questa mappa. Scegliere un dado valido.");
            return false;
        }
        return true;
    }
}