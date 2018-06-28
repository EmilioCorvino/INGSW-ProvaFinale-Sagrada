package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PublicObjectiveCardSlot;
import it.polimi.ingsw.model.cards.ToolCardSlot;
import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard;
import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCardsDeck;
import it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.PublicObjectiveCardsDeck;
import it.polimi.ingsw.model.cards.tool.ToolCardsDeck;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;
import it.polimi.ingsw.model.die.containers.RoundTrack;
import it.polimi.ingsw.model.die.containers.WindowPatternCardDeck;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.exceptions.EmptyException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * This class represents everything which is visible by all players.
 */
//todo This will probably be the observed class that will notify changes.
public class CommonBoard {

    /*todo This will be the controller connection.
    ControllerMaster controller;
    */

    public static final int NUMBER_OF_ROUNDS = 10;
    /**
     * Players connected to the match.
     */
    private final List<Player> players;


    /**
     * {@link it.polimi.ingsw.model.die.containers.DiceDraftPool} of the match.
     */
    private final DiceDraftPool draftPool;

    /**
     * {@link it.polimi.ingsw.model.die.containers.RoundTrack} of the match.
     */
    private final RoundTrack roundTrack;

    /**
     * List of the card slots containing public objective cards.
     * @see PublicObjectiveCardSlot
     */
    private final List<PublicObjectiveCardSlot> publicObjectiveCardSlots;

    /**
     * List of the card slots containing tool cards.
     * @see ToolCardSlot
     */
    private final List<ToolCardSlot> toolCardSlots;

    /**
     * Deck composed by {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard}.
     * @see PrivateObjectiveCardsDeck
     */
    private final PrivateObjectiveCardsDeck privateObjectiveCardsDeck;

    /**
     * Deck composed by {@link it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard}.
     * @see PublicObjectiveCardsDeck
     */
    private final PublicObjectiveCardsDeck publicObjectiveCardsDeck;

    /**
     * Deck composed by {@link it.polimi.ingsw.model.die.containers.WindowPatternCard}.
     */
    private final WindowPatternCardDeck windowPatternCardDeck;

    private final ToolCardsDeck toolCardsDeck;

    public CommonBoard() {
        this.players = new ArrayList<>();
        this.draftPool = new DiceDraftPool();
        this.roundTrack = new RoundTrack(NUMBER_OF_ROUNDS);
        this.publicObjectiveCardSlots = new ArrayList<>();
        this.toolCardSlots = new ArrayList<>();
        this.privateObjectiveCardsDeck = new PrivateObjectiveCardsDeck();
        this.publicObjectiveCardsDeck = new PublicObjectiveCardsDeck();
        this.windowPatternCardDeck = new WindowPatternCardDeck();
        this.toolCardsDeck = new ToolCardsDeck();
    }

    /**
     * This method is used for the parsing of the various decks and the population of card slots.
     */
    public void initializeBoard() {
        this.privateObjectiveCardsDeck.parseDeck();
        this.publicObjectiveCardsDeck.parseDeck();
        this.windowPatternCardDeck.parseDeck();
        this.toolCardsDeck.parseDeck();
        this.populatePubObjSlots();
        this.populateToolSlots();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public DiceDraftPool getDraftPool() {
        return draftPool;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public List<PublicObjectiveCardSlot> getPublicObjectiveCardSlots() {
        return publicObjectiveCardSlots;
    }

    public List<ToolCardSlot> getToolCardSlots() {
        return toolCardSlots;
    }

    public WindowPatternCardDeck getWindowPatternCardDeck() {
        return windowPatternCardDeck;
    }

    public PrivateObjectiveCardsDeck getPrivateObjectiveCardsDeck() {
        return privateObjectiveCardsDeck;
    }

    public PublicObjectiveCardsDeck getPublicObjectiveCardsDeck() {
        return publicObjectiveCardsDeck;
    }

    public ToolCardsDeck getToolCardsDeck() {
        return toolCardsDeck;
    }

    public Player getSpecificPlayer(String username) {
        for(Player p: players)
            if(username.equals(p.getPlayerName()))
                return p;
        throw new UnsupportedOperationException();
    }

    private void populateToolSlots(){
        //for (int i = 0; i < 3; i++) {
            //try {
                //this.getToolCardSlots().add(new ToolCardSlot(this.getToolCardsDeck().drawCard()));
                this.getToolCardSlots().add(new ToolCardSlot(this.getToolCardsDeck().getDeck().get(6)));
                this.getToolCardSlots().add(new ToolCardSlot(this.getToolCardsDeck().getDeck().get(7)));
                this.getToolCardSlots().add(new ToolCardSlot(this.getToolCardsDeck().getDeck().get(8)));
                //this.getToolCardSlots().get(i).setCost(1); //Default cost to use a tool card.
                this.getToolCardSlots().get(0).setCost(1); //Default cost to use a tool card
                this.getToolCardSlots().get(1).setCost(1); //Default cost to use a tool card
                this.getToolCardSlots().get(2).setCost(1); //Default cost to use a tool card
            /*
            } catch (EmptyException e) {
                SagradaLogger.log(Level.SEVERE, "Error during tool card drawing");
            }
            */
        //}
    }

    private void populatePubObjSlots(){
        for (int i = 0; i < 3; i++) {
            try {
                this.getPublicObjectiveCardSlots().add(new PublicObjectiveCardSlot( (APublicObjectiveCard) this.getPublicObjectiveCardsDeck().drawCard()));
            } catch (EmptyException e) {
                SagradaLogger.log(Level.SEVERE, "Error during public objective card drawing");
            }
        }
    }

    public void givePrivateObjCard(){
        for (Player p: this.players)
            try {
                p.setPrivateObjectiveCard( (PrivateObjectiveCard) this.privateObjectiveCardsDeck.drawCard());
            } catch (EmptyException e){
                SagradaLogger.log(Level.SEVERE, "Error during private objective card drawing");
            }
    }
}
