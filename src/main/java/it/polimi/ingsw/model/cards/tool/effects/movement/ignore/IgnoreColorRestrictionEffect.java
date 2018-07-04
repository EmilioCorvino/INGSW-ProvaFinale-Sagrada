package it.polimi.ingsw.model.cards.tool.effects.movement.ignore;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.cards.tool.effects.movement.MoveWithRestrictionsEffect;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.restrictions.ARestriction;
import it.polimi.ingsw.model.restrictions.ValueRestriction;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the tool effect that allows the user to place a die ignoring the
 * {@link it.polimi.ingsw.model.restrictions.ColorRestriction}, respecting only the {@link ValueRestriction} of the
 * personal {@link WindowPatternCard}.
 */
public class IgnoreColorRestrictionEffect extends MoveWithRestrictionsEffect {

    /**
     * This method moves the die ignoring {@link it.polimi.ingsw.model.restrictions.ColorRestriction}s.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInfoUnit object containing all the information needed to perform the move.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
        wp.createCopy();
        Cell[][] gw = wp.getGlassWindow();
        manager.incrementEffectCounter();

        if (!super.checkMoveAvailability(gw, setUpInfoUnit, manager.getEffectCounter())) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer(super.invalidMoveMessage);
            return;
        }

        Die chosenDie = wp.removeDieFromOriginal(setUpInfoUnit.getSourceIndex());
        wp.removeDieFromCopy(setUpInfoUnit.getSourceIndex());

        Cell desiredCell = new Cell(setUpInfoUnit.getDestinationIndex() / WindowPatternCard.MAX_COL,
                setUpInfoUnit.getDestinationIndex() % WindowPatternCard.MAX_COL);
        deleteColorRestriction(gw);

        if (super.isMoveIllegal(manager, wp, chosenDie, desiredCell, gw)) {
            super.restoreOriginalSituation(wp, setUpInfoUnit, chosenDie, manager);
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
     * This method deletes all the {@link it.polimi.ingsw.model.restrictions.ColorRestriction}s in the original
     * glass window.
     * @param gw the glass window to modify.
     */
    private void deleteColorRestriction(Cell[][] gw) {
        List<ARestriction> temporaryRuleSet;
        for(int i=0; i<WindowPatternCard.MAX_ROW; i++) {
            for (int j = 0; j < WindowPatternCard.MAX_COL; j++) {
                temporaryRuleSet = new ArrayList<>();
                if (!gw[i][j].isEmpty()) {
                    ARestriction valueRestriction = new ValueRestriction(gw[i][j].getContainedDie().getActualDieValue());
                    temporaryRuleSet.add(valueRestriction);
                } else {
                    int restrictionParameter = gw[i][j].getDefaultValueRestriction().getValue();
                    if (restrictionParameter != 0) {
                        ARestriction valueRestriction = new ValueRestriction(restrictionParameter);
                        temporaryRuleSet.add(valueRestriction);
                    }
                }
                gw[i][j].updateRuleSet(temporaryRuleSet);
            }
        }
    }
}