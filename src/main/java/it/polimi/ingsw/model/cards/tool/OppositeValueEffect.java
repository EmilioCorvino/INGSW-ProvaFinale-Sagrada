package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.Die;

/**
 * This class manage the effect of the tool cards that set a specific value for the chosen die.
 */
public class OppositeValueEffect extends AValueEffect {

    /**
     * Constructs this effect with a specific value.
     * @param offset
     */
    public OppositeValueEffect(int offset) {
        super(offset);
    }

    /**
     * This method computes the opposite value of a chosen die.
     * @param chosenDie the die on which to perform the action.
     * @return die with a value that is opposite of the value before.
     */
    public Die computeOppositeValue(Die chosenDie) {
        chosenDie.setActualDieValue(super.offset - chosenDie.getActualDieValue());
        super.checkValue(chosenDie.getActualDieValue());
        return chosenDie;
    }

    @Override
    public Die applyToolCardEffect(Die chosenDie) {
        return computeOppositeValue(chosenDie);
    }
}
