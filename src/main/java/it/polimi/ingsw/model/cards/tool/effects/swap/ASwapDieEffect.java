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

    /**
     * This method checks if exists at least one cell in which the user can place a die.
     * @param wp the window pattern of the player.
     * @param chosenDie the die to place.
     * @return true if exists at least one cell, false otherwise.
     */
    protected boolean checkExistingCellsToUse(WindowPatternCard wp, Die chosenDie) {
        Cell[][] gw = wp.getGlassWindowCopy();
        List<Cell> cellToUse = new ArrayList<>();
        for(int i=0; i< WindowPatternCard.getMaxRow(); i++)
            for (int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                wp.setDesiredCell(gw[i][j]);
                if (wp.canBePlaced(chosenDie, wp.getDesiredCell(), wp.getGlassWindow()))
                    cellToUse.add(gw[i][j]);
            }
        return cellToUse.size() > 0;
    }
}