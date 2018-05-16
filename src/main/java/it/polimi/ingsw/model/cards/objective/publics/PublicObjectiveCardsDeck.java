package it.polimi.ingsw.model.cards.objective.publics;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.cards.objective.AObjectiveCard;
import it.polimi.ingsw.model.cards.objective.AObjectiveCardsDeck;
import it.polimi.ingsw.model.cards.objective.publics.strategies.ColumnStrategy;
import it.polimi.ingsw.model.cards.objective.publics.strategies.DiagonalStrategy;
import it.polimi.ingsw.model.cards.objective.publics.strategies.RowStrategy;
import it.polimi.ingsw.model.cards.objective.publics.strategies.SetStrategy;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;

public class PublicObjectiveCardsDeck extends AObjectiveCardsDeck {

    public List<APublicObjectiveCard> getDeck() {
        return (List<APublicObjectiveCard>) this.deck;
    }

    /**
     * This method uses {@link Gson} to parse all private objective cards from a JSON file.
     */
    @Override
    public void parseDeck() {
        Gson gson = new Gson();
        //The TypeToken is needed to get the full parametrized type of the collection.
        Type listColorCards = new TypeToken<Vector<ColorPublicObjectiveCard>>(){}.getType();
        try(Reader file = new FileReader("./src/main/resources/cards/colorPublicObjectiveCards.json")){
            this.deck = gson.fromJson(file, listColorCards);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type listValueCards = new TypeToken<Vector<ValuePublicObjectiveCard>>(){}.getType();
        try(Reader file = new FileReader("./src/main/resources/cards/valuePublicObjectiveCards.json")){
            this.deck.addAll(gson.fromJson(file, listValueCards));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.assignStrategy();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

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
                default:            throw new IOException("Impossible to find strategy name on file.");
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
