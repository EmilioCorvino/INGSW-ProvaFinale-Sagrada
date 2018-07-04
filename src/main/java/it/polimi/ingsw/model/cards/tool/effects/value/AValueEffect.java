
package it.polimi.ingsw.model.cards.tool.effects.value;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.cards.tool.effects.AToolCardEffect;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

/**
 * This class manages the effects of the tool cards related to a change of the value of one or more dice. Said value is
 * not drafted, but willingly chosen by the player.
 */
public abstract class AValueEffect extends AToolCardEffect {

    /**
     * The offset that determines the interval in which the value that will be chosen has to be.
     */
    private int offset;

    /**
     * This constructor will set e default value for the offset in case it is not specified;
     */
    AValueEffect() {
        this.offset = 0;
    }

    /**
     * This constructor will set a specific value to use when the effect will be applied.
     * @param offset the specific value for the offset to set.
     */
    AValueEffect(int offset) {
        this.offset = offset;
    }

    /**
     * This method checks if the result of an operation on the value of die is legal.
     * @param dieValue the value of the die.
     * @return {@code true} if the value that will be set is coherent, {@code false} otherwise.
     */
    boolean checkValue(int dieValue) {
        return dieValue >= 1 && dieValue <= 6;
    }

    /**
     * This method checks if the value the user chooses is in the interval allowed by the effect of the tool card.
     * @param newValue the value chosen by the player.
     * @param originalValue the original value on which to execute the check.
     * @return {@code true} if the value is in the interval allowed by the effect of the tool card, {@code false}
     * otherwise.
     */
    boolean checkNewValue(int newValue, int originalValue) {
        if(this.offset != 0)
            return newValue >= originalValue - this.offset && newValue <= originalValue + this.offset;
        return true;
    }


    /**
     * This method executes the effect common to the value effects, which is the placement of the die.
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
        SetUpInformationUnit wpSetUpInfoUnit = new SetUpInformationUnit();
        wpSetUpInfoUnit.setColor(die.getDieColor());
        wpSetUpInfoUnit.setValue(die.getActualDieValue());
        wpSetUpInfoUnit.setDestinationIndex(setUpInfoUnit.getDestinationIndex());
        wpSetUpInfoUnit.setSourceIndex(setUpInfoUnit.getSourceIndex());

        manager.setMoveLegal(true);
        manager.getControllerMaster().getGameState().getCurrentTurn().incrementDieCount();

        Die dieToRemove = manager.getControllerMaster().getCommonBoard().getDraftPool().removeDieFromCopy(setUpInfoUnit.getSourceIndex());
        wp.setDesiredCell(desiredCell);
        wp.addDieToCopy(dieToRemove);
        manager.showPlacementResult(manager.getControllerMaster().getGameState().getCurrentPlayer(), wpSetUpInfoUnit);
    }
}