package it.polimi.ingsw.model.cards.objective.privates;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.cards.objective.AObjectiveCard;
import it.polimi.ingsw.model.cards.objective.AObjectiveCardsDeck;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

/**
 * This class contains a collection of {@link PrivateObjectiveCard}.
 * It initializes them parsing information from file.
 */
public class PrivateObjectiveCardsDeck extends AObjectiveCardsDeck {

    private static final String PRIV_OBJ_CARDS = "./src/main/resources/cards/objective/privateObjectiveCards.json";

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
        try(Reader file = new FileReader(PRIV_OBJ_CARDS)) {
            this.deck = gson.fromJson(file, listCards);
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
