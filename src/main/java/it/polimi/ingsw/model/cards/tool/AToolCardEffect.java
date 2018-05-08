package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.ADieContainer;
import it.polimi.ingsw.model.AEffectMoveDecorator;

/**
 * The class that manages the common characteristic of the effects of the tool cards.
 */
public abstract class AToolCardEffect extends AEffectMoveDecorator implements IToolCardEffect {

    ADieContainer sourceContainer;
    ADieContainer destinationContainer;



}
