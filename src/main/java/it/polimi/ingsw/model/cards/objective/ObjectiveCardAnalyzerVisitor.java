package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ColorPublicObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ValuePublicObjectiveCard;

/**
 * This class is used to call the right analyzing method from the selected {@link AObjectiveCard} and stores
 * the computed score from the card.
 * This class implements:
 * @see IObjectiveCardVisitor
 */
public class ObjectiveCardAnalyzerVisitor implements IObjectiveCardVisitor {

    /**
     * Score computed from the card.
     */
    private int scoreFromCard;

    public int getScoreFromCard() {
        return scoreFromCard;
    }

    private void setScoreFromCard(int scoreFromCard) {
        this.scoreFromCard = scoreFromCard;
    }

    /**
     * This method will launch the analysis of the window pattern card based on the {@link PrivateObjectiveCard}
     * algorithm.
     * @param privateObjectiveCard type of card to base the score computation on.
     * @param windowPatternCard window pattern card to analyze
     */
    @Override
    public void visit(PrivateObjectiveCard privateObjectiveCard, WindowPatternCard windowPatternCard) {
        this.setScoreFromCard(privateObjectiveCard.analyzeWindowPatternCard(windowPatternCard));
    }

    /**
     * This method will launch the analysis of the window pattern card based on the {@link ColorPublicObjectiveCard}
     * algorithm.
     * @param colorPublicObjectiveCard type of card to base the score computation on.
     * @param windowPatternCard window pattern card to analyze
     */
    @Override
    public void visit(ColorPublicObjectiveCard colorPublicObjectiveCard, WindowPatternCard windowPatternCard) {
        this.setScoreFromCard(colorPublicObjectiveCard.analyzeWindowPatternCard(windowPatternCard));
    }

    /**
     * This method will launch the analysis of the window pattern card based on the {@link ValuePublicObjectiveCard}
     * algorithm.
     * @param valuePublicObjectiveCard type of card to base the score computation on.
     * @param windowPatternCard window pattern card to analyze
     */
    @Override
    public void visit(ValuePublicObjectiveCard valuePublicObjectiveCard, WindowPatternCard windowPatternCard) {
        this.setScoreFromCard(valuePublicObjectiveCard.analyzeWindowPatternCard(windowPatternCard));
    }
}
