package it.polimi.ingsw.model.cards.tool.ignorerestrictionseffects;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.cards.tool.AToolCardEffect;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

/**
 * This class manges the tool effect that enables to move a die from a cell of the personal window pattern card
 * to another one.
 */
public class MoveWithRestrictionsEffect extends AToolCardEffect {

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

        playerWp.createCopy();
        glassWindowToConsider = playerWp.getGlassWindowCopy();

        if(!checkMoveAvailability(glassWindowToConsider, setUpInfoUnit)) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer(this.invalidMoveMessage);
            return;
        }

        Die chosenDie = playerWp.removeDieFromCopy(setUpInfoUnit.getSourceIndex());
        Cell desiredCell = new Cell(setUpInfoUnit.getDestinationIndex()/WindowPatternCard.MAX_COL , setUpInfoUnit.getDestinationIndex() % WindowPatternCard.MAX_COL);

        if(!playerWp.canBePlaced(chosenDie, desiredCell, glassWindowToConsider)) {
            manager.sendNotificationToCurrentPlayer(playerWp.getErrorMessage());
            manager.setMoveLegal(false);
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
     * @return true if all the conditions are satisfied, false otherwise.
     */
    boolean checkMoveAvailability(Cell[][] gw, SetUpInformationUnit info) {
        if (hasGlassWindowLessThanTwoDice(gw)) {
            this.setInvalidMoveMessage("La tua vetrata non ha abbastanza dadi! Non puoi muovere alcun dado.");
            return false;
        }
        if (gw[info.getSourceIndex()/WindowPatternCard.MAX_COL][info.getSourceIndex() % WindowPatternCard.MAX_COL].isEmpty()) {
            this.setInvalidMoveMessage("La cella sorgente è vuota.");
            return false;
        }
        if (!gw[info.getDestinationIndex()/WindowPatternCard.MAX_COL][info.getDestinationIndex() % WindowPatternCard.MAX_COL].isEmpty()) {
            this.setInvalidMoveMessage("La cella destinazione è piena.");
            return false;
        }
        return true;
    }

    /**
     * This method is used to check the validity of the movement, after the parameters have been validated by
     * {@link #checkMoveAvailability(Cell[][], SetUpInformationUnit)}.
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

    void restoreOriginalSituation(WindowPatternCard wp, SetUpInformationUnit setUpInfoUnit, Die chosenDie) {
        Cell previousCell = new Cell(setUpInfoUnit.getSourceIndex()/WindowPatternCard.MAX_COL,
                setUpInfoUnit.getSourceIndex() % WindowPatternCard.MAX_COL);
        wp.setDesiredCell(previousCell);
        wp.addDieToCopy(chosenDie);
        wp.overwriteOriginal();
    }

    String getInvalidMoveMessage() {
        return invalidMoveMessage;
    }

    private void setInvalidMoveMessage(String invalidMoveMessage) {
        this.invalidMoveMessage = invalidMoveMessage;
    }
}