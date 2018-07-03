package it.polimi.ingsw.model.cards.tool.effects.ignore;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.cards.tool.effects.AToolCardEffect;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

/**
 * This class manges the tool effect that enables to move a die from a cell of the personal window pattern card
 * to another one.
 */
public class MoveWithRestrictionsEffect extends AToolCardEffect {

    /**
     * Message containing the reason why the move didn't end well.
     */
    String invalidMoveMessage;

    /**
     * This method executes the specific effect of the tool that allows the user to move a die from a cell to another
     * of the personal window pattern card.
     * @param manager part of the controller that deals with the game play.e controller.
     * @param setUpInfoUnit the unit information to use to perform the effect.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        WindowPatternCard playerWp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
        Cell[][] glassWindowToConsider;

        if (manager.getEffectCounter() == 0) {
            playerWp.createCopy();
        }
        manager.incrementEffectCounter();
        glassWindowToConsider = playerWp.getGlassWindowCopy();

        if(!checkMoveAvailability(glassWindowToConsider, setUpInfoUnit, manager.getEffectCounter())) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer(this.invalidMoveMessage);
            manager.showRearrangementResult(manager.getControllerMaster().getGameState().getCurrentPlayer(), setUpInfoUnit);
            return;
        }

        Die chosenDie = playerWp.removeDieFromCopy(setUpInfoUnit.getSourceIndex());
        Cell desiredCell = new Cell(setUpInfoUnit.getDestinationIndex() / WindowPatternCard.MAX_COL,
                setUpInfoUnit.getDestinationIndex() % WindowPatternCard.MAX_COL);

        if (this.isMoveIllegal(manager, playerWp, chosenDie, desiredCell, glassWindowToConsider)) {
            this.restoreOriginalSituation(playerWp, setUpInfoUnit, chosenDie, manager);
            manager.showRearrangementResult(manager.getControllerMaster().getGameState().getCurrentPlayer(), setUpInfoUnit);
            return;
        }

        manager.setMoveLegal(true);
        playerWp.setDesiredCell(desiredCell);
        playerWp.addDieToCopy(chosenDie);
        setUpInfoUnit.setValue(chosenDie.getActualDieValue());
        setUpInfoUnit.setColor(chosenDie.getDieColor());
        manager.showRearrangementResult(manager.getControllerMaster().getGameState().getCurrentPlayer(), setUpInfoUnit);
    }

    /**
     * This method checks if a glass window has no dice.
     * @param gw the glass window to check.
     * @return true if it has no dice, false otherwise.
     */
    boolean hasGlassWindowLessThanTwoDice(Cell[][] gw) {
        int count = 0;
        for (int i = 0; i < WindowPatternCard.getMaxRow(); i++) {
            for (int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if (!gw[i][j].isEmpty()) {
                    count++;
                }
            }
        }
        return count < 2;
    }

    /**
     * This method checks if some important conditions to perform this move are satisfied or not.
     * @param gw the glass window to check.
     * @param info the information to use.
     * @param effectCounter represent the specific effect of the card.
     * @return true if all the conditions are satisfied, false otherwise.
     */
    boolean checkMoveAvailability(Cell[][] gw, SetUpInformationUnit info, int effectCounter) {
        if (hasGlassWindowLessThanTwoDice(gw)) {
            this.setInvalidMoveMessage("La tua vetrata non ha abbastanza dadi! Non puoi muovere alcun dado." + COMMANDS_HELP);
            return false;
        }
        if (gw[info.getSourceIndex()/WindowPatternCard.MAX_COL][info.getSourceIndex() % WindowPatternCard.MAX_COL].isEmpty()) {
            this.setInvalidMoveMessage("Spostamento n° " + effectCounter + ": la cella sorgente è vuota." + COMMANDS_HELP);
            return false;
        }
        if (!gw[info.getDestinationIndex()/WindowPatternCard.MAX_COL][info.getDestinationIndex() % WindowPatternCard.MAX_COL].isEmpty()) {
            this.setInvalidMoveMessage("Spostamento n° " + effectCounter + ": la cella destinazione è piena." + COMMANDS_HELP);
            return false;
        }
        return true;
    }

    /**
     * This method is used to check the validity of the movement, after the parameters have been validated by
     * {@link #checkMoveAvailability(Cell[][], SetUpInformationUnit, int)}.
     * @param manager part of the controller that deals with the game play.
     * @param wp {@link WindowPatternCard} to consider.
     * @param chosenDie {@link Die} that has been chosen.
     * @param desiredCell {@link Cell} that has been selected as destination.
     * @param gw glass window of the {@link WindowPatternCard} to consider.
     * @return {@code true} if the move is illegal, {@code false} otherwise.
     */
    boolean isMoveIllegal(GamePlayManager manager, WindowPatternCard wp, Die chosenDie, Cell desiredCell, Cell[][] gw) {
        if (!wp.canBePlaced(chosenDie, desiredCell, gw)) {
            manager.sendNotificationToCurrentPlayer(wp.getErrorMessage() + COMMANDS_HELP);
            manager.setMoveLegal(false);
            return true;
        }
        return false;
    }

    void restoreOriginalSituation(WindowPatternCard wp, SetUpInformationUnit setUpInfoUnit, Die chosenDie, GamePlayManager manager) {
        Cell previousCell = new Cell(setUpInfoUnit.getSourceIndex() / WindowPatternCard.MAX_COL,
                setUpInfoUnit.getSourceIndex() % WindowPatternCard.MAX_COL);
        wp.setDesiredCell(previousCell);
        wp.addDieToCopy(chosenDie);
        if (manager.getEffectCounter() < GamePlayManager.MAX_TOOL_EFFECTS_NUMBER) {
            wp.overwriteOriginal();
        }
    }

    String getInvalidMoveMessage() {
        return invalidMoveMessage;
    }

    private void setInvalidMoveMessage(String invalidMoveMessage) {
        this.invalidMoveMessage = invalidMoveMessage;
    }
}