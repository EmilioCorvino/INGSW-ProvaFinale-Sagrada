package it.polimi.ingsw.model.die.diecontainers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class WindowPatternCardDeck {

    private List<WindowPatternCard> deck;

    public WindowPatternCardDeck(){
        this.deck = new ArrayList<>();
    }

    public void parseDeck() {
        Gson gson = new Gson();
        try (Reader file = new FileReader("./src/main/resources/cards/windowPatternCard.json")) {
            this.deck = gson.fromJson(file, new TypeToken<List<WindowPatternCard>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<WindowPatternCard> getDeck() {
        return this.deck;
    }
}

