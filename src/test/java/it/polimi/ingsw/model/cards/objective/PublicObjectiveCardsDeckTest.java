package it.polimi.ingsw.model.cards.objective;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class PublicObjectiveCardsDeckTest {

    /**
     * Checks if the json loaded from file is the same as the one passed as a String.
     */
    @Test
    public void parseTest() {
        PublicObjectiveCardsDeck originalDeck = new PublicObjectiveCardsDeck();
        originalDeck.parseDeck();
        Gson gson = new Gson();
        Type listColorCards = new TypeToken<Vector<ColorPublicObjectiveCard>>(){}.getType();
        List<APublicObjectiveCard> testCards = gson.fromJson("[\n" +
                "  {\n" +
                "    \"id\": 200,\n" +
                "    \"name\": \"Colori diversi - Riga\",\n" +
                "    \"description\": \"Righe senza colori ripetuti\",\n" +
                "    \"pointsForIteration\": 6,\n" +
                "    \"strategy\": \"row\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 201,\n" +
                "    \"name\":\"Colori diversi - Colonna\",\n" +
                "    \"description\":\"Colonne senza colori ripetuti\",\n" +
                "    \"pointsForIteration\": 5,\n" +
                "    \"strategy\": \"column\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 202,\n" +
                "    \"name\": \"Diagonali Colorate\",\n" +
                "    \"description\": \"Numero di dadi dello stesso colore diagonalmente adiacenti\",\n" +
                "    \"pointsForIteration\": 0,\n" +
                "    \"strategy\": \"diagonal\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 203,\n" +
                "    \"name\": \"Varietà di Colore\",\n" +
                "    \"description\": \"Set di dadi di ogni colore ovunque\",\n" +
                "    \"pointsForIteration\": 4,\n" +
                "    \"strategy\": \"set\"\n" +
                "  }\n" +
                "]", listColorCards);

        Type listValueCards = new TypeToken<Vector<ValuePublicObjectiveCard>>(){}.getType();
        testCards.addAll(gson.fromJson("[\n" +
                "  {\n" +
                "    \"id\": 204,\n" +
                "    \"name\": \"Sfumature diverse - Riga\",\n" +
                "    \"description\": \"Righe senza sfumature ripetute\",\n" +
                "    \"pointsForIteration\": 5,\n" +
                "    \"strategy\": \"row\",\n" +
                "    \"shade\": \"ALL\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 205,\n" +
                "    \"name\": \"Sfumature diverse - Colonna\",\n" +
                "    \"description\": \"Colonne senza sfumature ripetute\",\n" +
                "    \"pointsForIteration\": 4,\n" +
                "    \"strategy\": \"column\",\n" +
                "    \"shade\": \"ALL\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 206,\n" +
                "    \"name\": \"Sfumature Chiare\",\n" +
                "    \"description\": \"Set di 1 & 2 ovunque\",\n" +
                "    \"pointsForIteration\": 2,\n" +
                "    \"strategy\": \"set\",\n" +
                "    \"shade\": \"LIGHT\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 207,\n" +
                "    \"name\": \"Sfumature Medie\",\n" +
                "    \"description\": \"Set di 3 & 4 ovunque\",\n" +
                "    \"pointsForIteration\": 2,\n" +
                "    \"strategy\": \"set\",\n" +
                "    \"shade\": \"MEDIUM\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 208,\n" +
                "    \"name\": \"Sfumature Scure\",\n" +
                "    \"description\": \"Set di 5 & 6 ovunque\",\n" +
                "    \"pointsForIteration\": 2,\n" +
                "    \"strategy\": \"set\",\n" +
                "    \"shade\": \"DARK\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 209,\n" +
                "    \"name\": \"Sfumature Diverse\",\n" +
                "    \"description\": \"Set di dadi di ogni valore ovunque\",\n" +
                "    \"pointsForIteration\": 5,\n" +
                "    \"strategy\": \"set\",\n" +
                "    \"shade\": \"ALL\"\n" +
                "  }\n" +
                "]", listValueCards));

        assertEquals(testCards, originalDeck.getDeck());
    }

    /**
     * Checks if the drawn card gets effectively removed.
     */
    @Test
    public void drawTest() {
        PublicObjectiveCardsDeck originalDeck = new PublicObjectiveCardsDeck();
        originalDeck.parseDeck();
        PublicObjectiveCardsDeck testDeck = new PublicObjectiveCardsDeck();
        testDeck.parseDeck();
        AObjectiveCard card = originalDeck.drawCard();

        assertTrue(testDeck.contains(card));
        assertFalse(originalDeck.contains(card));
    }
}