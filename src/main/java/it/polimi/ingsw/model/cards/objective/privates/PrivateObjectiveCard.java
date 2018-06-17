package it.polimi.ingsw.model.cards.objective.privates;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.AObjectiveCard;
import it.polimi.ingsw.model.cards.objective.IObjectiveCardVisitor;

/**
 * This class represents the private objective card from the game. Each player has its one which is not displayed
 * to the others.
 * The class is filled by a JSON parser {@link com.google.gson.Gson} in the {@link PrivateObjectiveCardsDeck} class.
 * @see PrivateObjectiveCardsDeck
 */
public class PrivateObjectiveCard extends AObjectiveCard {

    /**
     * Name of the card.
     */
    private String name;

    /**
     * Color of the dice to consider in the computation of the score.
     */
    @SerializedName("color")
    private Color cardColor;

    /**
     * Description of the card: says what the effect does.
     */
    private String description;

    /**
     * This constructor is meant for testing purposes.
     */
    public PrivateObjectiveCard(int id, String name, Color color, String description) {
        this.id = id;
        this.name = name;
        this.cardColor = color;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    Color getCardColor() {
        return cardColor;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Computes the score gained from this private objective card by adding up the values of the dice, contained in
     * the player's window pattern card, whose color is the same as the one of this card.
     * @param windowPatternCard pattern card analyzed to get the score.
     * @return the score gained from this private objective card.
     */
    @Override
    public int analyzeWindowPatternCard(WindowPatternCard windowPatternCard) {
        int score = 0;
        for(int i = 0; i < WindowPatternCard.getMaxRow(); i++) {
            for(int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if(!(windowPatternCard.getGlassWindow()[i][j].isEmpty()) &&
                        (windowPatternCard.getGlassWindow()[i][j].getContainedDie().getDieColor().equals(this.getCardColor()))) {
                    score += windowPatternCard.getGlassWindow()[i][j].getContainedDie().getActualDieValue();
                }
            }
        }
        return score;
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

    @Override
    public String toString() {
        return "Private Objective Card: " + this.getName() + "--> " +
                this.getDescription() + "\n";
    }
}
