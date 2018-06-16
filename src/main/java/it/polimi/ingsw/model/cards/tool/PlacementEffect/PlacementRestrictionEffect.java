package it.polimi.ingsw.model.cards.tool.PlacementEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.cards.tool.AToolCardEffect;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;

/**
 * This class manges the tool effect that enables to move a die from a cell of the personal window pattern card
 * to one another.
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
        WindowPatternCard playerWp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
        Die chosenDie = playerWp.getGlassWindow()[setUpInfoUnit.getSourceIndex()/WindowPatternCard.MAX_COL][setUpInfoUnit.getSourceIndex() % WindowPatternCard.MAX_COL].getContainedDie();
        playerWp.setDesiredCell(new Cell(setUpInfoUnit.getDestinationIndex()/WindowPatternCard.MAX_COL , setUpInfoUnit.getDestinationIndex() % WindowPatternCard.MAX_COL));

        if(!playerWp.canBePlaced(chosenDie, playerWp.getDesiredCell())) {
            manager.showNotification(playerWp.getErrorMessage());
            return;
        }

        playerWp.removeDie(setUpInfoUnit.getSourceIndex());
        playerWp.addDie(chosenDie);
    }
}