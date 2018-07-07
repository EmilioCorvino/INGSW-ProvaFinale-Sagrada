package it.polimi.ingsw.server.model.cards.objective.publics;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.server.model.cards.objective.AObjectiveCard;
import it.polimi.ingsw.server.model.cards.objective.publics.strategies.*;
import it.polimi.ingsw.server.model.die.containers.WindowPatternCard;

/**
 * Generic public objective card.
 * @see ColorPublicObjectiveCard
 * @see ValuePublicObjectiveCard
 */
public abstract class APublicObjectiveCard extends AObjectiveCard {

    /**
     * Name of the card.
     */
    private String name;

    /**
     * Description of the card: says what the effect does.
     */
    private String description;

    /**
     * Number of points given each time the conditions of the card are respected
     */
    private int pointsForCompletion;


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
    private transient IScoreComputationStrategy strategy;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPointsForCompletion() {
        return pointsForCompletion;
    }

    String getStrategyName() {
        return strategyName;
    }

    public IScoreComputationStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IScoreComputationStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * This method analyzes a specific window pattern card with a policy related to the public objective cards.
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

    @Override
    public String toString() {
        String s = "Public Objective Card: " + this.getName() + "--> " +
                this.getDescription();
        //If it's 0 it refers to the card "Diagonali Colorate", whose description is self-explanatory.
        if(this.getPointsForCompletion() != 0) {
            s += ": guadagni " + this.getPointsForCompletion() + " punti ogni volta che completi l'obiettivo" + "\n";
            return s;
        }
        return s + "\n";
    }
}