package it.polimi.ingsw.model.cards.tool.ValueEffects;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.move.DiePlacementMove;
import it.polimi.ingsw.model.move.IMove;

/**
 * This class manages the effect of those tool cards that allow the player to increase the value of a chosen die.
 */
public class ChooseValueEffect extends AValueEffect {

    /**
     *
     */
    private static final int INCREASE_CODE = 0;

    /**
     *
     */
    private static final int DECREASE_CODE = 1;

    /**
     * Constructs this effect with the default value.
     */
    public ChooseValueEffect() {
        super();
    }

    /**
     * Constructs this effect with a specific value.
     * @param offset: the value to set.
     */
    public ChooseValueEffect(int offset) {
        super(offset);
    }

    /**
     * This method increases the original die value by a factor of one.
     * @param die: the die on which to perform the action.
     * @return the die with the increased value or with the original value in case the checks fail.
     */
    public Die increaseDieValue(Die die) {
        int newValue = die.getActualDieValue() + 1;
        if(super.checkNewValue(newValue, die.getOriginalDieValue()) && super.checkValue(newValue)) {
            die.setActualDieValue(newValue);
            return die;
        }
        return die;
    }

    /**
     * This method decreases the original die value by a factor of one.
     * @param chosenDie the die on which to perform the action.
     * @return the die with the decreased value or with the original value in case the checks fail.
     */
    public Die decreaseDieValue(Die chosenDie) {
        int newValue = chosenDie.getActualDieValue() - 1;
        if(super.checkNewValue(newValue, chosenDie.getOriginalDieValue()) && super.checkValue(newValue)) {
            chosenDie.setActualDieValue(newValue);
            return chosenDie;
        }
        return chosenDie;
    }

    /**
     * This metho
     * @param manager
     * @param setUpInfoUnit
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {
        Die die = manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice().get(setUpInfoUnit.getSourceIndex());

        if(setUpInfoUnit.getExtraParam() == INCREASE_CODE)
            die.setActualDieValue(increaseDieValue(die).getActualDieValue());

        if(setUpInfoUnit.getExtraParam() == DECREASE_CODE)
            die.setActualDieValue(decreaseDieValue(die).getActualDieValue());

        //tell the controller to show the result
        IMove move = new DiePlacementMove();
        move.executeMove(manager, setUpInfoUnit);
    }

    public static int getIncreaseCode() {
        return INCREASE_CODE;
    }

    public static int getDecreaseCode() {
        return DECREASE_CODE;
    }
}