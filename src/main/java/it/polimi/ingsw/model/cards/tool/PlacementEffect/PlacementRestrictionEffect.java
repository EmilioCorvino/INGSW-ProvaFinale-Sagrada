package it.polimi.ingsw.model.cards.tool.PlacementEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.cards.tool.AToolCardEffect;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

/**
 * This class manges the tool effect that enables to move a die from a cell of the personal window pattern card
 * to one another.
 */
public class PlacementRestrictionEffect extends AToolCardEffect {

    private String invalidMove;

    /**
     * This method executes the specific effect of the tool that allows the user to move a die from a cell to another
     * of the personal window pattern card.
     * @param manager the controller.
     * @param setUpInfoUnit the unit information to use to perform the effect.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {


        WindowPatternCard playerWp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
        playerWp.createCopy();

        if(!checkMoveAvailability(playerWp.getGlassWindowCopy(), setUpInfoUnit)) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer(this.invalidMove);
        }

        Die chosenDie = playerWp.getGlassWindow()[setUpInfoUnit.getSourceIndex()/WindowPatternCard.MAX_COL][setUpInfoUnit.getSourceIndex() % WindowPatternCard.MAX_COL].getContainedDie();
        playerWp.setDesiredCell(new Cell(setUpInfoUnit.getDestinationIndex()/WindowPatternCard.MAX_COL , setUpInfoUnit.getDestinationIndex() % WindowPatternCard.MAX_COL));

        if(!playerWp.canBePlaced(chosenDie, playerWp.getDesiredCell(), playerWp.getGlassWindow())) {
            manager.sendNotificationToCurrentPlayer(playerWp.getErrorMessage());
            manager.setMoveLegal(false);
            return;
        }

        manager.setMoveLegal(true);
        playerWp.setDesiredCell(new Cell(setUpInfoUnit.getDestinationIndex()/WindowPatternCard.MAX_COL, setUpInfoUnit.getDestinationIndex() % WindowPatternCard.MAX_COL));
        playerWp.addDie(playerWp.removeDie(setUpInfoUnit.getSourceIndex()));
        setUpInfoUnit.setValue(chosenDie.getActualDieValue());
        setUpInfoUnit.setColor(chosenDie.getDieColor());
        manager.showRearrangementResult(manager.getControllerMaster().getGameState().getCurrentPlayer(), setUpInfoUnit);

    }

    /**
     * This method checks if a glass window has no dice.
     * @param gw the glass window to check.
     * @return true if it has no dice, false otherwise.
     */
    public boolean isGlassWindowEmpty(Cell[][] gw) {
        for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
            for(int j=0; j< WindowPatternCard.getMaxCol(); j++) {
                if(!gw[i][j].isEmpty())
                    return false;
            }
        return true;
    }

    /**
     * This method checks if some important conditions to perform this move are satisfied or not.
     * @param gw the glass window to check.
     * @param info the information to use.
     * @return true if all the conditions are satisfied, false otherwise.
     */
    public boolean checkMoveAvailability(Cell[][] gw, SetUpInformationUnit info) {
        if(isGlassWindowEmpty(gw)) {
            this.setInvalidMove("La tua vetrata non ha dadi! non puoi muovere alcun dado.");
            return false;
        }
        if(gw[info.getSourceIndex()/WindowPatternCard.MAX_COL][info.getSourceIndex() % WindowPatternCard.MAX_COL].isEmpty()) {
           this.setInvalidMove("La cella sorgente è vuota");
            return false;
        }
        if(!gw[info.getDestinationIndex()/WindowPatternCard.MAX_COL][info.getDestinationIndex() % WindowPatternCard.MAX_COL].isEmpty()) {
            this.setInvalidMove("La cella destinazione è piena");
            return false;
        }
        return true;
    }

    public String getInvalidMove() {
        return invalidMove;
    }

    public void setInvalidMove(String invalidMove) {
        this.invalidMove = invalidMove;
    }
}