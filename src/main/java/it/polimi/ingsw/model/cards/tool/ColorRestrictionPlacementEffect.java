package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.ColorRestriction;
import it.polimi.ingsw.model.WindowPatternCard;

/**
 *
 */
public class ColorRestrictionPlacementEffect extends PlacementRestrictionEffect {

    private final ColorRestriction colorRestriction;

    public ColorRestrictionPlacementEffect(ColorRestriction colorRestriction) {
        this.colorRestriction = colorRestriction;
    }

    public ColorRestriction getColorRestriction() {
        return colorRestriction;
    }

    private void substituteRuleSet(WindowPatternCard windowPatternCard) {

    }

}
