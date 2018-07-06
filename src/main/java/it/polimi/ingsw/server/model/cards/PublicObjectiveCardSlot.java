package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.cards.objective.publics.APublicObjectiveCard;

/**
 * A class that contains a {@link it.polimi.ingsw.server.model.cards.objective.publics.APublicObjectiveCard}.
 * The score gets computed fetching card slots instead of cards, to be sure that only cards actually drawn are taken
 * into consideration.
 */
public class PublicObjectiveCardSlot {

    /**
     * {@link APublicObjectiveCard} contained in the slot.
     */
    private final APublicObjectiveCard publicObjectiveCard;

    public PublicObjectiveCardSlot(APublicObjectiveCard card) {
        this.publicObjectiveCard = card;
    }

    public APublicObjectiveCard getPublicObjectiveCard() {
        return publicObjectiveCard;
    }
}
