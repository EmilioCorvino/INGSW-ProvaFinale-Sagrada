package it.polimi.ingsw.model.cards.objective;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;

/**
 * This class contains a collection of private objective cards.
 * It initializes them parsing information from file..
 */
public class PrivateObjectiveCardsDeck extends AObjectiveCardsDeck {

    /**
     * Left empty because of gson parser.
     */
    PrivateObjectiveCardsDeck() {
        //Left empty because of gson parser.
    }

    public List<PrivateObjectiveCard> getDeck() {
        return (List<PrivateObjectiveCard>) this.deck;
    }

    /**
     * This method uses Gson to parse all private objective cards from a JSON file.
     */
    public void parseDeck() {
        Gson gson = new Gson();
        //The TypeToken is needed to get the full parametrized type of the collection.
        Type listCards = new TypeToken<Vector<PrivateObjectiveCard>>(){}.getType();
        try(Reader file = new FileReader("./src/main/resources/cards/privateObjectiveCards.json")){
            this.deck = gson.fromJson(file, listCards);
        } catch (IOException e) {
            e.printStackTrace();
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