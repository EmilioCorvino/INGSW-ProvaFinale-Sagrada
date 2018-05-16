package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.publics.ValuePublicObjectiveCard;

/**
 * Contains the possible "shades" (i.e. fixed set of values) a die can represent. Each shade corresponds to two possible
 * values: LIGHT = 1 or 2, MEDIUM = 3 ore 4, DARK = 5 or 6. ALL represents all possible die values.
 * It is used to compute the score with different strategies by the
 * {@link ValuePublicObjectiveCard} class.
 */
public enum Shade {
    LIGHT,
    MEDIUM,
    DARK,
    ALL;
}
