package it.polimi.ingsw.model.ToolCards;

import it.polimi.ingsw.model.Die;

/**
 * This class manages the effect of those tool cards that allow the player to change the value of a chosen die.
 */
public class ChooseValueEffect extends ValueEffect {

    /**
     * This method increase the original die value by a factor of one.
     * @param die: the die on which to perform the action.
     * @return the die with the increased value or with the original value in case the checks fail.
     */
    public Die increaseDieValue(Die die) {
        int newValue = die.getDieValue() + 1;
        if(checkNewValue(newValue, die) && super.checkValue(newValue)) {
            die.setDieValue(newValue);
            return die;
        }
        return die;
    }

    /**
     * This method decrease the original die value by a factor of one.
     * @param die: the die on which to perform the action.
     * @return the die with the decreased value or with the original value in case the checks fail.
     */
    public Die decreaseValue(Die die) {
        int newValue = die.getDieValue() - 1;
        if(checkNewValue(newValue, die) && super.checkValue(newValue)) {
            die.setDieValue(newValue);
            return die;
        }
        return die;
    }

    /**
     * This method checks if the value the user chooses is in the interval allowed by the effect of the tool card.
     * @param newValue: the value chosen by the player.
     * @param die: the die on which to execute the check.
     * @return true if the value is in the interval allowed by the effect of the tool card.
     */
    private boolean checkNewValue(int newValue, Die die) {
        if(super.offset != 0)
            return newValue >= die.getDieValue() - super.offset && newValue <= die.getDieValue() + super.offset;
        return true;
    }
}
