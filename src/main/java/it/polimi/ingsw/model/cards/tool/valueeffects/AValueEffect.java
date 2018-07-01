
package it.polimi.ingsw.model.cards.tool.valueeffects;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.cards.tool.AToolCardEffect;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the effects of the tool cards related to a change of the value of one or more dice.
 */
public abstract class AValueEffect extends AToolCardEffect {

    /**
     * The offset that determines the interval in which the value that will be chosen must respect.
     */
    private int offset;

    /**
     *
     */
    protected Die chosenDie;

    public Die getChosenDie() {
        return chosenDie;
    }

    public void setChosenDie(Die chosenDie) {
        this.chosenDie = chosenDie;
    }

    /**
     * This constructor will set e default value for the offset in case it is not specified;
     */
    AValueEffect() {
        this.offset = 0;
    }

    /**
     * This constructor will set a specific value to use when the effect will be applied.
     * @param offset: the specific value for the offset to set.
     */
    AValueEffect(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * This method checks if the result of an operation on the value of die is a right value.
     * @param dieValue: the value of the die.
     * @return true if the value that will be set is coherent.
     */
    boolean checkValue(int dieValue) {
        return dieValue >=1 && dieValue <= 6;
    }

    /**
     * This method checks if the value the user chooses is in the interval allowed by the effect of the tool card.
     * @param newValue: the value chosen by the player.
     * @param originalValue: the original value on which to execute the check.
     * @return true if the value is in the interval allowed by the effect of the tool card.
     */
    boolean checkNewValue(int newValue, int originalValue) {
        if(this.offset != 0)
            return newValue >= originalValue - this.offset && newValue <= originalValue + this.offset;
        return true;
    }

    boolean checkExistingCellsToUse(WindowPatternCard wp, Die chosenDie) {
        Cell[][] gw = wp.getGlassWindowCopy();
        List<Cell> cellToUse = new ArrayList<>();
        for(int i=0; i< WindowPatternCard.MAX_ROW; i++)
            for (int j = 0; j < WindowPatternCard.MAX_COL; j++) {
                wp.setDesiredCell(gw[i][j]);
                if (wp.canBePlaced(chosenDie, wp.getDesiredCell(), wp.getGlassWindowCopy()))
                    cellToUse.add(gw[i][j]);
            }
        return cellToUse.size() > 0;
    }

    /**
     * This method executes the effect common to the value effects.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInfoUnit object containing all the information needed to perform the move.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit){
        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();

        Cell desiredCell = new Cell(setUpInfoUnit.getDestinationIndex() / WindowPatternCard.getMaxCol(), setUpInfoUnit.getDestinationIndex() % WindowPatternCard.getMaxCol());

        Die die = manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDiceCopy().get(setUpInfoUnit.getSourceIndex());

        if(!wp.canBePlaced(die, desiredCell, wp.getGlassWindowCopy())) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer(wp.getErrorMessage() + COMMANDS_HELP);
            return;
        }

        //Generation of SetUpInformationUnits to send to the view.
        //this goes in a proper method and show placement result takes one parameter as input.
        SetUpInformationUnit wpSetUpInfoUnit = new SetUpInformationUnit();
        wpSetUpInfoUnit.setColor(die.getDieColor());
        wpSetUpInfoUnit.setValue(die.getActualDieValue());
        wpSetUpInfoUnit.setDestinationIndex(setUpInfoUnit.getDestinationIndex());
        wpSetUpInfoUnit.setSourceIndex(setUpInfoUnit.getSourceIndex());

        manager.setMoveLegal(true);

        Die dieToRemove = manager.getControllerMaster().getCommonBoard().getDraftPool().removeDieFromCopy(setUpInfoUnit.getSourceIndex());
        wp.setDesiredCell(desiredCell);
        wp.addDieToCopy(dieToRemove);
        manager.showPlacementResult(manager.getControllerMaster().getGameState().getCurrentPlayer(), wpSetUpInfoUnit);
    }
}