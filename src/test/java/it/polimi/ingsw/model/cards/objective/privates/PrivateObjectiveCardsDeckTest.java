package it.polimi.ingsw.model.cards.objective.privates;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.cards.objective.AObjectiveCard;
import it.polimi.ingsw.exceptions.EmptyException;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;

public class PrivateObjectiveCardsDeckTest {

    /**
     * Checks if the json loaded from file is the same as the one passed as a String.
     */
    @Test
    public void parseTest() {
        PrivateObjectiveCardsDeck originalDeck = new PrivateObjectiveCardsDeck();
        originalDeck.parseDeck();
        Gson gson = new Gson();
        Type listCards = new TypeToken<Vector<PrivateObjectiveCard>>(){}.getType();
        List<PrivateObjectiveCard> testCards = gson.fromJson("[\n" +
                "  {\n" +
                "    \"id\": 100,\n" +
                "    \"name\": \"Sfumature Rosse\",\n" +
                "    \"color\":\"RED\",\n" +
                "    \"description\": \"Somma dei valori su tutti i dadi rossi\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 101,\n" +
                "    \"name\": \"Sfumature Gialle\",\n" +
                "    \"color\": \"YELLOW\",\n" +
                "    \"description\": \"Somma dei valori su tutti i dadi gialli\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 102,\n" +
                "    \"name\": \"Sfumature Verdi\",\n" +
                "    \"color\": \"GREEN\",\n" +
                "    \"description\": \"Somma dei valori su tutti i dadi verdi\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 103,\n" +
                "    \"name\": \"Sfumature Blu\",\n" +
                "    \"color\": \"BLUE\",\n" +
                "    \"description\": \"Somma dei valori su tutti i dadi blu\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 104,\n" +
                "    \"name\": \"Sfumature Viola\",\n" +
                "    \"color\": \"PURPLE\",\n" +
                "    \"description\": \"Somma dei valori su tutti i dadi viola\"\n" +
                "  }\n" +
                "]", listCards);

        assertEquals(testCards, originalDeck.getDeck());
    }

    /**
     * Checks if the drawn card gets effectively removed.
     */
    @Test
    public void drawTest() {
        PrivateObjectiveCardsDeck originalDeck = new PrivateObjectiveCardsDeck();
        originalDeck.parseDeck();
        PrivateObjectiveCardsDeck testDeck = new PrivateObjectiveCardsDeck();
        testDeck.parseDeck();

        try {
            AObjectiveCard card = originalDeck.drawCard();
            assertTrue(testDeck.contains(card));
            assertFalse(originalDeck.contains(card));
        } catch (EmptyException e) {
            System.err.println(e.getMessage());
        }
    }
}
