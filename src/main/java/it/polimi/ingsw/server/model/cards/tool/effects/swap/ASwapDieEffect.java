package it.polimi.ingsw.server.model.cards.tool.effects.swap;

import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.server.model.cards.tool.effects.AToolCardEffect;
import it.polimi.ingsw.server.model.die.containers.ADieContainer;

/**
 * This class manages the tool effect that swaps two dice between two die containers.
 */
abstract class ASwapDieEffect extends AToolCardEffect {

    /**
     * This method moves a die from a die container to another one, it takes the information about the move form the informationUnit.
     * @param source The die container from where the die is going to be moved.
     * @param destination The die container where the die is moved.
     * @param informationUnit The containers of all the information needed for the moving.
     */
    protected abstract void swapDie(ADieContainer source, ADieContainer destination, SetUpInformationUnit informationUnit);
}