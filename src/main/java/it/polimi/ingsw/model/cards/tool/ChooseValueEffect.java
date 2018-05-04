package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.Die;

/**
 * This class manages the effect of those tool cards that allow the player to increase the value of a chosen die.
 */
public class ChooseValueEffect extends AValueEffect {

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
     *
     * @param chosenDie the die the player chooses.
     * @return
     */
    @Override
    public Die applyToolCardEffect(Die chosenDie) {
        if(super.symbol.equals("+"))
            return increaseDieValue(chosenDie);

        if(super.symbol.equals("-"))
            return decreaseDieValue(chosenDie);

        return chosenDie;
    }
}