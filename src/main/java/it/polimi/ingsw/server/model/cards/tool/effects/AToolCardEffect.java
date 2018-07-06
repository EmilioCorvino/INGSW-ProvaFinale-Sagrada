package it.polimi.ingsw.server.model.cards.tool.effects;

import it.polimi.ingsw.server.model.move.AMove;

/**
 * This class represents a generic effect of a {@link it.polimi.ingsw.server.model.cards.tool.ToolCard}.
 * It has been created to represent the abstract concept of "effect", distinct to the more general {@link AMove}.
 * Any addiction that has to be common to the whole Tool Cards effects set can be done here.
 */
public abstract class AToolCardEffect extends AMove {

}
