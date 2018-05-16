package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard;
import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCardsDeck;
import it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.PublicObjectiveCardsDeck;
import it.polimi.ingsw.model.exceptions.EmptyException;

import java.util.List;
import java.util.Random;

/**
 * This class is the abstraction of a deck. It contains a collection of objective cards.
 * For the implementation of the parsing, see extending classes.
 * @see PrivateObjectiveCardsDeck
 * @see PublicObjectiveCardsDeck
 */
public abstract class AObjectiveCardsDeck {

    /**
     * Contains all the objective cards. The use of the wildcard is limited to the fact that there can be
     * lists of {@link PrivateObjectiveCard} or {@link APublicObjectiveCard}. There can never be a List
     * containing both, since the parsing methods only fill {@link PrivateObjectiveCardsDeck} and
     * {@link PublicObjectiveCardsDeck} with {@link PrivateObjectiveCard} or {@link APublicObjectiveCard}, respectively.
     * There is never a case in which a deck contains both type of cards.
     */
    protected List<? extends AObjectiveCard> deck;

    /**
     * Parses cards from a file. See extending classes for implementation.
     */
    public abstract void parseDeck();

    /**
     * Draws a random card and removes it from the deck.
     * @return a random card from the deck.
     */
    public AObjectiveCard drawCard() throws EmptyException {
        if(this.deck.isEmpty()) {
            throw new EmptyException("The deck is empty");
        }
        int index = new Random().nextInt(this.deck.size());
        return this.deck.remove(index);
    }

    /**
     * Checks if the passed card is contained in the deck. To do so, it compares the id of the card in input with
     * the ids of the cards which are in the deck.
     * @param card to look for in the deck.
     * @return {@code true} if the card is in the deck, {@code false} otherwise.
     */
    public boolean contains(AObjectiveCard card) {
        for(AObjectiveCard c: this.deck) {
            if(card.equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the deck in input is equal to {@code this}. It does so by checking if the decks have the same size and
     * contain the same cards.
     * @param obj deck to check.
     * @return {@code true} if the decks are equals, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(this.getClass() != obj.getClass()) {
            return false;
        }
        AObjectiveCardsDeck deckToCheck = (AObjectiveCardsDeck) obj;
        if(this.deck.size() == deckToCheck.deck.size()) {
            for(AObjectiveCard card: this.deck) {
                if(!deckToCheck.contains(card)) {
                    return false; //The deck has a different card from the deck in input.
                }
            }
            return true; //Both decks contain the same cards.
        }
        return false; //The decks have different sizes.
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * @return the deck as a String.
     */
    public abstract String toString();
}
