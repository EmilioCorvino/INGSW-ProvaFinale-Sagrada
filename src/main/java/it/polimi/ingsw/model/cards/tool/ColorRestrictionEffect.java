package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.restrictions.ColorRestriction;

/**
 *
 */
public class ColorRestrictionEffect extends PlacementRestrictionEffect {

    private final ColorRestriction colorRestriction;

    public ColorRestrictionEffect(ColorRestriction colorRestriction) {
        this.colorRestriction = colorRestriction;
    }

    public ColorRestriction getColorRestriction() {
        return colorRestriction;
    }

    private void substituteRuleSet(WindowPatternCard windowPatternCard) {

    }

}
