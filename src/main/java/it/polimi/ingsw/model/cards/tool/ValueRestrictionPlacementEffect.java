package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.ValueRestriction;

/**
 *
 */
public class ValueRestrictionPlacementEffect extends PlacementRestrictionEffect {

    private final ValueRestriction valueRestriction;

    public ValueRestrictionPlacementEffect(ValueRestriction valueRestriction) {
        this.valueRestriction = valueRestriction;
    }

    public ValueRestriction getValueRestriction() {
        return valueRestriction;
    }
}
