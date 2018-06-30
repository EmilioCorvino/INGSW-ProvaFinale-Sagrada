package it.polimi.ingsw.model.cards.tool.ignorerestrictionseffects;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.restrictions.ARestriction;
import it.polimi.ingsw.model.restrictions.ValueRestriction;

import java.util.ArrayList;
import java.util.List;


/**
 * This class manages the tool effect that allows the user to place a die ignoring the color restriction but
 * respecting only the value restriction of the personal window pattern card.
 */
public class IgnoreColorRestrictionEffect extends MoveWithRestrictionsEffect {

    /**
     * This method moves the die ignoring color restrictions.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInfoUnit object containing all the information needed to perform the move.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
        wp.createCopy();

        Die chosenDie = wp.getGlassWindowCopy()[setUpInfoUnit.getSourceIndex()/WindowPatternCard.MAX_COL][setUpInfoUnit.getSourceIndex() % WindowPatternCard.MAX_COL].getContainedDie();
        Cell[][] gw = wp.getGlassWindow();
        Cell desiredCell = new Cell(setUpInfoUnit.getDestinationIndex()/WindowPatternCard.MAX_COL , setUpInfoUnit.getDestinationIndex() % WindowPatternCard.MAX_COL);
        deleteColorRestriction(gw);

        if(!wp.canBePlaced(chosenDie, desiredCell, gw)) {
            wp.overwriteOriginal();
            manager.sendNotificationToCurrentPlayer(wp.getErrorMessage() + "Usa 'comandi' per ulteriori informazioni.");
            manager.setMoveLegal(false);
            return;
        }

        super.setRestrictionsIgnored(true);
        super.executeMove(manager, setUpInfoUnit);
    }

    /**
     * This method deletes all the color restriction in the original glass window.
     * @param gw the glass window to modify.
     */
    private void deleteColorRestriction(Cell[][] gw) {
        List<ARestriction> list;
        for(int i=0; i<WindowPatternCard.MAX_ROW; i++)
            for(int j=0; j<WindowPatternCard.MAX_COL; j++) {
                list = new ArrayList<>();
                if(!gw[i][j].isEmpty()) {
                    ARestriction valueRestriction = new ValueRestriction(gw[i][j].getContainedDie().getActualDieValue());
                    list.add(valueRestriction);
                    gw[i][j].updateRuleSet(list);
                } else {
                    int restrictionParameter = gw[i][j].getDefaultValueRestriction().getValue();
                    if(restrictionParameter != 0) {
                        ARestriction valueRestriction = new ValueRestriction(restrictionParameter);
                        list.add(valueRestriction);
                        gw[i][j].updateRuleSet(list);
                    }
                }
            }
    }
}