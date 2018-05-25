package it.polimi.ingsw.model.die.diecontainers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.exceptions.EmptyException;
import it.polimi.ingsw.model.restrictions.ARestriction;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WindowPatternCardDeck {

    /**
     * The list of window pattern card available
     */
    private List<WindowPatternCard> availableWP;

    /**
     * The list of window pattern card associated in front and back configuration.
     */
    private List<List<WindowPatternCard>> deck;

    public WindowPatternCardDeck(){
        this.availableWP = new ArrayList<>();
        this.deck = new ArrayList<>();
    }

    /**
     * This method create a list of window pattern card.
     */
    public void parseDeck() {
        Gson gson = new Gson();
        try (Reader file = new FileReader("./src/main/resources/cards/windowPatternCard.json")) {
            this.availableWP = gson.fromJson(file, new TypeToken<List<WindowPatternCard>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.populateCellsRuleSet();
        this.associateFrontAndBack();
    }

    /**
     * This method draw a card from the deck.
     * @return A couple of window pattern card
     */
    public List<WindowPatternCard> drawCard()throws EmptyException {
        if(this.deck.isEmpty()) {
            throw new EmptyException("The deck is empty");
        }
        int index = new Random().nextInt(this.deck.size());
        return this.deck.remove(index);
    }

    /**
     * This method will populate the role set of all cells with the color and value default restriction.
     */
    private void populateCellsRuleSet(){
        for(WindowPatternCard wp : availableWP) {
            for (int i = 0; i < wp.MAX_ROW; i++) {
                for (int j = 0; j < wp.MAX_COL; j++) {
                    List<ARestriction> rules = new ArrayList<>();
                    rules.add(wp.getGlassWindow()[i][j].getDefaultColorRestriction());
                    rules.add(wp.getGlassWindow()[i][j].getDefaultValueRestriction());
                    wp.getGlassWindow()[i][j].updateRuleSet(rules);
                }
            }
        }
    }

    /**
     * This method move wp with consecutive id in the same list, one is the front of the card and the other one is the back.
     */
    private void associateFrontAndBack(){
        for(int i = 0; i < availableWP.size()/2; i++){
            deck.add(new ArrayList<>());
            for (int j = 0; j < availableWP.size(); j = j+2){
                deck.get(i).add(availableWP.get(j));
                deck.get(i).add(availableWP.get(j+1));
            }
        }

    }
    public List<WindowPatternCard> getAvailableWP() {
        return this.availableWP;
    }

    public List<List<WindowPatternCard>> getDeck() {
        return deck;
    }
}

