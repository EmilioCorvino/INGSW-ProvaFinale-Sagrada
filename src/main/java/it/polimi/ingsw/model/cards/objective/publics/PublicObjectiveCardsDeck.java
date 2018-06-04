package it.polimi.ingsw.model.cards.objective.publics;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.cards.objective.AObjectiveCard;
import it.polimi.ingsw.model.cards.objective.AObjectiveCardsDeck;
import it.polimi.ingsw.model.cards.objective.publics.strategies.ColumnStrategy;
import it.polimi.ingsw.model.cards.objective.publics.strategies.DiagonalStrategy;
import it.polimi.ingsw.model.cards.objective.publics.strategies.RowStrategy;
import it.polimi.ingsw.model.cards.objective.publics.strategies.SetStrategy;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

/**
 * This class contains a collection of {@link APublicObjectiveCard}.
 * It initializes them parsing information from file.
 */
public class PublicObjectiveCardsDeck extends AObjectiveCardsDeck {

    /**
     * Path of colorPublicObjectiveCards.json.
     */
    private static final String COLOR_PUB_OBJ_CARDS = "./src/main/resources/cards/objective/colorPublicObjectiveCards.json";

    /**
     * Path of valuePublicObjectiveCards.json.
     */
    private static final String VALUE_PUB_OBJ_CARDS = "./src/main/resources/cards/objective/valuePublicObjectiveCards.json";

    public List<APublicObjectiveCard> getDeck() {
        return (List<APublicObjectiveCard>) this.deck;
    }

    /**
     * This method uses {@link Gson} to parse all {@link APublicObjectiveCard}s from a JSON file.
     */
    @Override
    public void parseDeck() {
        Gson gson = new Gson();
        //The TypeToken is needed to get the full parametrized type of the collection.
        Type listColorCards = new TypeToken<Vector<ColorPublicObjectiveCard>>(){}.getType();
        try(Reader file = new FileReader(COLOR_PUB_OBJ_CARDS)){
            this.deck = gson.fromJson(file, listColorCards);
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to access color public objective cards file", e);
        }

        Type listValueCards = new TypeToken<Vector<ValuePublicObjectiveCard>>(){}.getType();
        try(Reader file = new FileReader(VALUE_PUB_OBJ_CARDS)){
            this.deck.addAll(gson.fromJson(file, listValueCards));
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to access value public objective cards file", e);
        }

        try {
            this.assignStrategy();
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Assigns strategies to loaded cards.
     * @throws IOException is thrown if there is no strategy corresponding to the strategy name found on file.
     */
    private void assignStrategy() throws IOException {
        for(APublicObjectiveCard card: this.getDeck()) {
            switch (card.getStrategyName()) {
                case "row":         card.setStrategy(new RowStrategy());
                                    break;
                case "column":      card.setStrategy(new ColumnStrategy());
                                    break;
                case "set":         card.setStrategy(new SetStrategy());
                                    break;
                case "diagonal":    card.setStrategy(new DiagonalStrategy());
                                    break;
                default:            throw new IOException("Illegal strategy found on file.");
            }
        }
    }

    @Override
    public String toString() {
        String s = "The Public Objective Cards Deck contains:\n";
        StringBuilder builder = new StringBuilder(s);
        for(AObjectiveCard card: this.getDeck()) {
            builder.append(card.toString());
        }
        return builder.toString();
    }
}
