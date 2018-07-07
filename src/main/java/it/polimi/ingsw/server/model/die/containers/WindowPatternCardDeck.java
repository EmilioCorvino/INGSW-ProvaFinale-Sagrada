package it.polimi.ingsw.server.model.die.containers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.common.Color;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.EmptyException;
import it.polimi.ingsw.server.model.die.Cell;
import it.polimi.ingsw.server.model.restrictions.IRestriction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class WindowPatternCardDeck {

    /**
     * The list of window pattern card available
     */
    private List<WindowPatternCard> availableWP;

    /**
     * The list of window pattern card associated in front and back configuration.
     */
    private List<List<WindowPatternCard>> deck;

    private static final String WINDOW_PATTER_CARDS = "/cards/windowPatternCard.json";

    public WindowPatternCardDeck(){
        this.availableWP = new ArrayList<>();
        this.deck = new ArrayList<>();
    }

    /**
     * This method create a list of window pattern card.
     */
    public void parseDeck() {
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(WindowPatternCardDeck.class.getResourceAsStream(WINDOW_PATTER_CARDS)))) {
            this.availableWP = gson.fromJson(reader, new TypeToken<List<WindowPatternCard>>(){}.getType());
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Error during parsing, file not read");
        }
        this.populateCellsRuleSetAndInitializeCopy();
        this.associateFrontAndBack();
    }

    /**
     * This method draw a card from the deck.
     * @return A couple of window pattern card
     */
    public List<WindowPatternCard> drawCard() throws EmptyException {
        if(this.deck.isEmpty()) {
            throw new EmptyException("The deck is empty");
        }
        int index = new Random().nextInt(this.deck.size());
        return this.deck.remove(index);
    }

    /**
     * This method will populate the role set of all cells with the color and value default restriction.
     */
    private void populateCellsRuleSetAndInitializeCopy(){
        for(WindowPatternCard wp : availableWP) {
            for (int i = 0; i < WindowPatternCard.MAX_ROW; i++) {
                for (int j = 0; j < WindowPatternCard.MAX_COL; j++) {
                    wp.getGlassWindow()[i][j].setRuleSetCell(new ArrayList<>());
                    List<IRestriction> rules = new ArrayList<>();
                    if(!(wp.getGlassWindow()[i][j].getDefaultColorRestriction().getColor().equals(Color.BLANK)))
                        rules.add(wp.getGlassWindow()[i][j].getDefaultColorRestriction());
                    if(wp.getGlassWindow()[i][j].getDefaultValueRestriction().getValue() != 0)
                        rules.add(wp.getGlassWindow()[i][j].getDefaultValueRestriction());
                    wp.getGlassWindow()[i][j].updateRuleSet(rules);
                }
            }
            wp.setGlassWindowCopy(new Cell[WindowPatternCard.MAX_ROW][WindowPatternCard.MAX_COL]);
        }
    }

    /**
     * This method move wp with consecutive id in the same list, one is the front of the card and the other one is the back.
     */
    private void associateFrontAndBack(){
        for(int i = 0; i < availableWP.size()/2; i++){
            deck.add(new ArrayList<>());
            for (int j = 0; j <2; j++)
                deck.get(i).add(availableWP.get(i*2 + j));
        }
    }

    public List<WindowPatternCard> getAvailableWP() {
        return this.availableWP;
    }

    public List<List<WindowPatternCard>> getDeck() {
        return deck;
    }
}

