package it.polimi.ingsw.model.cards.tool.ignorerestrictionseffects;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.restrictions.ARestriction;
import it.polimi.ingsw.model.restrictions.ColorRestriction;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the effect that allows the player to place a die ignoring the value restriction but
 * respecting only the color restriction of the personal window pattern card.
 */
public class IgnoreValueRestrictionEffect extends MoveWithRestrictionsEffect {

    /**
     * This method moves the die ignoring value restrictions.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInfoUnit object containing all the information needed to perform the move.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
        wp.createCopy();
        Cell[][] gw = wp.getGlassWindow();

        if (!super.checkMoveAvailability(gw, setUpInfoUnit)) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer(super.invalidMove);
            return;
        }

        Die chosenDie = wp.removeDieFromOriginal(setUpInfoUnit.getSourceIndex());
        wp.removeDieFromCopy(setUpInfoUnit.getSourceIndex());

        Cell desiredCell = new Cell(setUpInfoUnit.getDestinationIndex()/WindowPatternCard.MAX_COL , setUpInfoUnit.getDestinationIndex() % WindowPatternCard.MAX_COL);
        deleteValueRestriction(gw);

        if (!super.checkMoveLegality(manager, wp, chosenDie, desiredCell, gw)) {
            return;
        }

        manager.setMoveLegal(true);
        wp.setDesiredCell(desiredCell);
        wp.addDieToCopy(chosenDie);
        setUpInfoUnit.setValue(chosenDie.getActualDieValue());
        setUpInfoUnit.setColor(chosenDie.getDieColor());
        manager.showRearrangementResult(manager.getControllerMaster().getGameState().getCurrentPlayer(), setUpInfoUnit);
    }

    /**
     * This method deletes all the color restriction in the original glass window.
     * @param gw the glass window to modify.
     */
    private void deleteValueRestriction(Cell[][] gw) {
        List<ARestriction> list;
        for(int i=0; i<WindowPatternCard.MAX_ROW; i++) {
            for (int j = 0; j < WindowPatternCard.MAX_COL; j++) {
                list = new ArrayList<>();
                if (!gw[i][j].isEmpty()) {
                    ARestriction colorRestriction = new ColorRestriction(gw[i][j].getContainedDie().getDieColor());
                    list.add(colorRestriction);
                } else {
                    Color restrictionParameter = gw[i][j].getDefaultColorRestriction().getColor();
                    if (restrictionParameter.equals(Color.BLANK)) {
                        ARestriction colorRestriction = new ColorRestriction(restrictionParameter);
                        list.add(colorRestriction);
                    }
                }
                gw[i][j].updateRuleSet(list);
            }
        }
    }
}