package it.polimi.ingsw.model.cards.objective.publics;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.Shade;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.IObjectiveCardVisitor;
import it.polimi.ingsw.model.cards.objective.publics.strategies.IScoreComputationStrategy;

/**
 * This type of {@link APublicObjectiveCard} relies on Die {@link it.polimi.ingsw.model.Shade} to compute the score,
 * applying the selected positional {@link IScoreComputationStrategy}.
 * Each card is filled with {@link com.google.gson.Gson} parser.
 */
public class ValuePublicObjectiveCard extends APublicObjectiveCard {

    /**
     * {@link Shade} used by the card.
     */
    @SerializedName("shade")
    private Shade cardShade;

    public Shade getShade() {
        return cardShade;
    }

    /**
     * Applies the card strategy (based on {@link it.polimi.ingsw.model.Shade} to compute the score.
     * @param windowPatternCard pattern card analyzed to get the score.
     * @return the score from the card.
     */
    @Override
    public int analyzeWindowPatternCard(WindowPatternCard windowPatternCard) {
        return this.getStrategy().applyValueStrategy(this, windowPatternCard);
    }

    /**
     * Allows the {@link IObjectiveCardVisitor} to visit this class and launch the window pattern card analysis.
     * @param objectiveCardVisitor visitor to accept.
     * @param windowPatternCard window pattern card to analyze.
     */
    @Override
    public void accept(IObjectiveCardVisitor objectiveCardVisitor, WindowPatternCard windowPatternCard) {
        objectiveCardVisitor.visit(this, windowPatternCard);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
