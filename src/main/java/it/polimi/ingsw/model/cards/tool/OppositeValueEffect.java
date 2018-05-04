package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.Die;

/**
 *
 */
public class OppositeValueEffect extends AValueEffect {

    public OppositeValueEffect(int offset) {
        super.setOffset(offset);
    }

    /**
     *
     * @param chosenDie
     * @return
     */
    public Die computeOppositeValue(Die chosenDie) {
        chosenDie.setActualDieValue(super.offset - chosenDie.getActualDieValue());
        super.checkValue(chosenDie.getActualDieValue());
        return chosenDie;
    }

    /**
     *
     * @param chosenDie the die the player chooses.
     * @return
     */
    @Override
    public Die applyToolCardEffect(Die chosenDie) {
        return computeOppositeValue(chosenDie);
    }
}
