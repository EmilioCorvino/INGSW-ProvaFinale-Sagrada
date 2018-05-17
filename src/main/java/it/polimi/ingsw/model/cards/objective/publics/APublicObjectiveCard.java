package it.polimi.ingsw.model.cards.objective.publics;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.AObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.strategies.*;

/**
 * Generic publics objective card.
 * @see ColorPublicObjectiveCard
 * @see ValuePublicObjectiveCard
 */
public abstract class APublicObjectiveCard extends AObjectiveCard {

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
     * Name of the strategy
     */
    @SerializedName("strategy")
    private String strategyName;

    /**
     * Score computation strategy to apply. It is transient so that {@link com.google.gson} deserializer
     * doesn't try to instantiate it.
     * Implementing classes:
     * @see RowStrategy
     * @see ColumnStrategy
     * @see DiagonalStrategy
     * @see SetStrategy
     */
    transient IScoreComputationStrategy strategy;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPointsForIteration() {
        return pointsForIteration;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public IScoreComputationStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IScoreComputationStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * This method analyzes a specific window pattern card with a policy related to the publics objective card.
     * @param windowPatternCard pattern card analyzed to get the score.
     * @return the score given by a publics objective card in relation to the window pattern card.
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

    @Override
    public String toString() {
        String s = "Public Objective Card: " + this.getName() + "--> " +
                this.getDescription();
        //If it's 0 it refers to the card "Diagonali Colorate", whose description is self-explanatory.
        if(this.getPointsForIteration() != 0) {
            s += ": guadagni " + this.getPointsForIteration() + " punti ogni volta che completi l'obiettivo" + "\n";
            return s;
        }
        return s + "\n";
    }
}
