package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.publics.ValuePublicObjectiveCard;

/**
 * Contains the possible "shades" (i.e. fixed set of values) a die can represent. Each shade corresponds to two possible
 * values: LIGHT = 1 or 2, MEDIUM = 3 or 4, DARK = 5 or 6. ALL represents all possible die values.
 * It is used to compute the score with different strategies by the
 * {@link ValuePublicObjectiveCard} class.
 */
public enum Shade {
    LIGHT(1, 2),
    MEDIUM(3, 4),
    DARK(5, 6),
    ALL;

    private int lighterShade;

    private int darkerShade;

    Shade() {
        //Used to allow ALL to be part of this enum.
    }

    Shade(int lighterShade, int darkerShade) {
        this.lighterShade = lighterShade;
        this.darkerShade = darkerShade;
    }

    public int getLighterShade() {
        return lighterShade;
    }

    public int getDarkerShade() {
        return darkerShade;
    }
}
