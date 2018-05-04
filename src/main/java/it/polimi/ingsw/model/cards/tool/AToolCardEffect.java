package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.AEffectMoveDecorator;
import it.polimi.ingsw.model.Die;

/**
 * The class that manages the common characteristic of the effects of the tool cards.
 */
public abstract class AToolCardEffect extends AEffectMoveDecorator implements IToolCardEffect {

    /**
     * This method will apply a specific effect to the die the player chooses.
     * @param chosenDie the die the player chooses.
     * @return the modified die.
     */
    public abstract Die applyToolCardEffect(Die chosenDie);


}
