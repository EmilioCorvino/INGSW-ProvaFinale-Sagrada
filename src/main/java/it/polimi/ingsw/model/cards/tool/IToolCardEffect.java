package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.Die;

/**
 * This interface contains all the useful methods to manage the tool cards and the correspondent effects.
 */
public interface IToolCardEffect {

    /**
     * This method will apply a specific effect to the die the player chooses.
     * @param chosenDie the die the player chooses.
     * @return the modified die.
     */
    public Die applyToolCardEffect(Die chosenDie);

}
