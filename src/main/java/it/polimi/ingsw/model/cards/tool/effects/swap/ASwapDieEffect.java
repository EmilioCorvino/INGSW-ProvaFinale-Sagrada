package it.polimi.ingsw.model.cards.tool.effects.swap;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.tool.effects.AToolCardEffect;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the tool effect that swaps two dice between two die containers.
 */
public abstract class ASwapDieEffect extends AToolCardEffect {

    /**
     * This method executes the swap of two dice.
     * @param die1 the die from the source container.
     * @param die2 the die from the destination container.
     */
    public void swapDice(Die die1, Die die2) {
        Die temp = new Die(0, Color.BLANK);

        temp.setActualDieValue(die2.getActualDieValue());
        temp.setDieColor(die2.getDieColor());

        die2.setActualDieValue(die1.getActualDieValue());
        die2.setDieColor(die1.getDieColor());

        die1.setActualDieValue(temp.getActualDieValue());
        die1.setDieColor(temp.getDieColor());
    }
    
}