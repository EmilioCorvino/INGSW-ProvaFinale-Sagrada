package it.polimi.ingsw.model.cards.objective;

import com.google.gson.annotations.SerializedName;
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

    /**
     * Constructor left empty for gson.
     */
    PrivateObjectiveCard() {
        //Left empty because of gson parser.
    }

    /**
     * This constructor is meant for testing purposes.
     */
    PrivateObjectiveCard(int id, String name, Color color, String description) {
        this.id = id;
        this.name = name;
        this.cardColor = color;
        this.description = description;
    }

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
