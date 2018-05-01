package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.Die;

/**
 * This class manages the effect of those tool cards that allow the player to change the value of a chosen die.
 */
public class ChooseValueEffect extends ValueEffect {



    /**
     * Contructs this effect with the default value.
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
     * This method increase the original die value by a factor of one.
     * @param die: the die on which to perform the action.
     * @return the die with the increased value or with the original value in case the checks fail.
     */
    public Die increaseDieValue(Die die) {
        int newValue = die.getActualDieValue() + 1;
        if(checkNewValue(newValue, die.getOriginalDieValue()) && super.checkValue(newValue)) {
            die.setActualDieValue(newValue);
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
        int newValue = die.getActualDieValue() - 1;
        if(checkNewValue(newValue, die.getOriginalDieValue()) && super.checkValue(newValue)) {
            die.setActualDieValue(newValue);
            return die;
        }
        return die;
    }

    /**
     * This method checks if the value the user chooses is in the interval allowed by the effect of the tool card.
     * @param newValue: the value chosen by the player.
     * @param originalValue: the original value on which to execute the check.
     * @return true if the value is in the interval allowed by the effect of the tool card.
     */
    private boolean checkNewValue(int newValue, int originalValue) {
        if(super.offset != 0)
            return newValue >= originalValue - super.offset && newValue <= originalValue + super.offset;
        return true;
    }
}
