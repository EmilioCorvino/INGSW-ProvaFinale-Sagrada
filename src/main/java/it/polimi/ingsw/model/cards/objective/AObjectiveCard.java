package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ColorPublicObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ValuePublicObjectiveCard;

/**
 * This class abstracts the concept of objective cards, which are those cards that give points. These points
 * are earned differently, depending on the concrete objective card.
 */
public abstract class AObjectiveCard {

    protected int id;

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

    /**
     * This methods accepts a visitor and launches his method, which allows the right analysis of the window pattern
     * card based on which type of objective card is computing the score.
     * @param objectiveCardVisitor visitor to accept.
     * @param windowPatternCard window pattern card to analyze.
     * @see IObjectiveCardVisitor
     * @see ObjectiveCardAnalyzerVisitor
     * @see PrivateObjectiveCard
     * @see APublicObjectiveCard
     * @see ColorPublicObjectiveCard
     * @see ValuePublicObjectiveCard
     */
    public abstract void accept(IObjectiveCardVisitor objectiveCardVisitor, WindowPatternCard windowPatternCard);
}
