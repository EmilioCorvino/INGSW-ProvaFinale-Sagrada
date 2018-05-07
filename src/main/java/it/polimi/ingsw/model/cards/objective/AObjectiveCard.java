package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.WindowPatternCard;

/**
 * This class abstracts the concept of objective cards, which are those cards that give points. These points
 * are earned differently, depending on the concrete objective card.
 */
public abstract class AObjectiveCard {

    int id;

    public int getId() {
        return id;
    }

    /**
     * Checks if the card in input is equal to {@code this}.
     * @param obj card to check.
     * @return {@code true} if the card in input is equal to {@code this}, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(this.getClass() != obj.getClass()) {
            return false;
        }
        AObjectiveCard card = (AObjectiveCard) obj;
        return this.getId() == card.getId();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * This method analyzes a specific window pattern card with a policy related to the objective card.
     * @param windowPatternCard pattern card analyzed to get the score.
     * @return the score given by an objective card in relation to the window pattern card.
     */
    public abstract int analyzeWindowPatternCard(WindowPatternCard windowPatternCard);
}
