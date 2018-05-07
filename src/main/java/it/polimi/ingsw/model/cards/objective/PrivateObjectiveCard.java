package it.polimi.ingsw.model.cards.objective;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.WindowPatternCard;

/**
 * This class represents the private objective card from the game. Each player has its one which is not displayed
 * to the others.
 * The class is filled by a JSON parser (GSON) in the PrivateObjectiveCardDeck class.
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
     * Description of the card, says what the effect does.
     */
    private String description;

    public String getName() {
        return name;
    }

    public Color getCardColor() {
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

        return score;
    }

    @Override
    public String toString() {
        return "Private Objective Card: " + this.getName() + "--> " +
                this.getDescription() + "\n";
    }
}
