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

    String invalidMove;

    private boolean restrictionsIgnored = false;

    /**
     * This method executes the specific effect of the tool that allows the user to move a die from a cell to another
     * of the personal window pattern card.
     * @param manager the controller.
     * @param setUpInfoUnit the unit information to use to perform the effect.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        WindowPatternCard playerWp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
        Cell[][] glassWindowToConsider;

        if(!areRestrictionsIgnored()) {
            playerWp.createCopy();
            glassWindowToConsider = playerWp.getGlassWindowCopy();

            if(!checkMoveAvailability(glassWindowToConsider, setUpInfoUnit)) {
                manager.setMoveLegal(false);
                manager.sendNotificationToCurrentPlayer(this.invalidMove);

                //Restore the original situation.
                this.setRestrictionsIgnored(false);
                return;
            }
        } else {
            glassWindowToConsider = playerWp.getGlassWindow();

            //Restore the original situation.
            this.setRestrictionsIgnored(false);
        }

        Die chosenDie = playerWp.removeDie(setUpInfoUnit.getSourceIndex());
        Cell desiredCell = new Cell(setUpInfoUnit.getDestinationIndex()/WindowPatternCard.MAX_COL , setUpInfoUnit.getDestinationIndex() % WindowPatternCard.MAX_COL);

        if(!playerWp.canBePlaced(chosenDie, desiredCell, glassWindowToConsider)) {
            manager.sendNotificationToCurrentPlayer(playerWp.getErrorMessage());
            manager.setMoveLegal(false);
            return;
        }

        manager.setMoveLegal(true);
        playerWp.setDesiredCell(desiredCell);
        playerWp.addDie(chosenDie);
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
        for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
            for(int j=0; j< WindowPatternCard.getMaxCol(); j++) {
                if(!gw[i][j].isEmpty())
                    count++;
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
            this.setInvalidMove("La tua vetrata non ha abbastanza dadi! Non puoi muovere alcun dado.");
            return false;
        }
        if (gw[info.getSourceIndex()/WindowPatternCard.MAX_COL][info.getSourceIndex() % WindowPatternCard.MAX_COL].isEmpty()) {
            this.setInvalidMove("La cella sorgente è vuota");
            return false;
        }
        if (!gw[info.getDestinationIndex()/WindowPatternCard.MAX_COL][info.getDestinationIndex() % WindowPatternCard.MAX_COL].isEmpty()) {
            this.setInvalidMove("La cella destinazione è piena");
            return false;
        }
        return true;
    }

    boolean checkMoveLegality(GamePlayManager manager, WindowPatternCard wp, Die chosenDie, Cell desiredCell, Cell[][] gw) {
        if (!wp.canBePlaced(chosenDie, desiredCell, gw)) {
            wp.overwriteOriginal();
            manager.sendNotificationToCurrentPlayer(wp.getErrorMessage() + " Digita 'comandi' per visualizzare i comandi disponibili.");
            manager.setMoveLegal(false);
            return false;
        }
        return true;
    }

    String getInvalidMove() {
        return invalidMove;
    }

    private void setInvalidMove(String invalidMove) {
        this.invalidMove = invalidMove;
    }

    void setRestrictionsIgnored(boolean restrictionsIgnored) {
        this.restrictionsIgnored = restrictionsIgnored;
    }

    private boolean areRestrictionsIgnored() {
        return restrictionsIgnored;
    }
}