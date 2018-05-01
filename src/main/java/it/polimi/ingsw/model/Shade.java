package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.DieValueOutOfBoundsException;

/**
 * Contains the possible "shades" (i.e. fixed set of values) a die can represent. Each shade corresponds to two possible
 * values: the smaller number is the lighter shade, whilst the bigger one is the darker shade.
 * It is used to compute the score with different strategies by the ValuePublicObjectiveCard class.
 */
public enum Shade {
    LIGHT(1, 2),
    MEDIUM(3, 4),
    DARK(5, 6);

    private final int lighterShade;
    private final int darkerShade;

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

    /**
     * This method returns a Shade given a die value.
     * @param die Die whose Shade you want to know.
     * @return the shade of the die.
     * @throws DieValueOutOfBoundsException thrown if an invalid die value is given as input.
     */
    public static Shade getShadeFromValue(Die die) throws DieValueOutOfBoundsException {
        //todo maybe move this exception to Die or to the class creating them
        if(die.getActualDieValue() < 1 || die.getActualDieValue() > 6) {
            throw new DieValueOutOfBoundsException("Invalid die value input");
        } else if (die.getActualDieValue() == LIGHT.getLighterShade() || die.getActualDieValue() == LIGHT.getDarkerShade()) {
            return LIGHT;
        } else if (die.getActualDieValue() == MEDIUM.getLighterShade() || die.getActualDieValue() == MEDIUM.getDarkerShade()) {
            return MEDIUM;
        } else if (die.getActualDieValue() == DARK.getLighterShade() || die.getActualDieValue() == DARK.getDarkerShade()) {
            return DARK;
        }
        return null; //Should never arrive here.
    }
}
