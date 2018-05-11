package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.WindowPatternCard;

/**
 * Generic public objective card.
 * @see ColorPublicObjectiveCard
 * @see ValuePublicObjectiveCard
 */
public abstract class APublicObjectiveCard extends AObjectiveCard{

    /**
     * Name of the card.
     */
    String name;

    /**
     * Description of the card: says what the effect does.
     */
    String description;

    /**
     * Number of points given each time the conditions of the card are respected
     */
    int pointsForIteration;

    /**
     * Score computation strategy to apply.
     * Implementing classes:
     * @see RowStrategy
     * @see ColumnStrategy
     * @see DiagonalStrategy
     * @see SetStrategy
     */
    IScoreComputationStrategy strategy;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPointsForIteration() {
        return pointsForIteration;
    }

    public IScoreComputationStrategy getStrategy() {
        return strategy;
    }

    /**
     * This method analyzes a specific window pattern card with a policy related to the public objective card.
     * @param windowPatternCard pattern card analyzed to get the score.
     * @return the score given by a public objective card in relation to the window pattern card.
     */
    @Override
    public abstract int analyzeWindowPatternCard(WindowPatternCard windowPatternCard);

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
