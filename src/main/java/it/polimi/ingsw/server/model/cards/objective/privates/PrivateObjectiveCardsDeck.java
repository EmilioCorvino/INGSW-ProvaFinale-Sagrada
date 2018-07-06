package it.polimi.ingsw.server.model.cards.objective.privates;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.server.model.cards.objective.AObjectiveCard;
import it.polimi.ingsw.server.model.cards.objective.AObjectiveCardsDeck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

/**
 * This class contains a collection of {@link PrivateObjectiveCard}.
 * It initializes them parsing information from file.
 */
public class PrivateObjectiveCardsDeck extends AObjectiveCardsDeck {

    private static final String PRIV_OBJ_CARDS = "/cards/objective/privateObjectiveCards.json";

    public List<PrivateObjectiveCard> getDeck() {
        return (List<PrivateObjectiveCard>) this.deck;
    }

    /**
     * This method uses {@link Gson} to parse all {@link PrivateObjectiveCard}s from a JSON file.
     */
    public void parseDeck() {
        Gson gson = new Gson();
        //The TypeToken is needed to get the full parametrized type of the collection.
        Type listCards = new TypeToken<Vector<PrivateObjectiveCard>>(){}.getType();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(PrivateObjectiveCardsDeck.class.getResourceAsStream(PRIV_OBJ_CARDS)))) {
            this.deck = gson.fromJson(reader, listCards);
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to access private objective cards file", e);
        }
    }

    @Override
    public String toString() {
        String s = "The Private Objective Cards Deck contains:\n";
        StringBuilder builder = new StringBuilder(s);
        for(AObjectiveCard card: this.getDeck()) {
            builder.append(card.toString());
        }
        return builder.toString();
    }
}
