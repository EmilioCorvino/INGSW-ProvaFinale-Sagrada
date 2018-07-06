package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.EmptyException;
import it.polimi.ingsw.server.model.cards.PublicObjectiveCardSlot;
import it.polimi.ingsw.server.model.cards.ToolCardSlot;
import it.polimi.ingsw.server.model.cards.objective.privates.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.cards.objective.privates.PrivateObjectiveCardsDeck;
import it.polimi.ingsw.server.model.cards.objective.publics.APublicObjectiveCard;
import it.polimi.ingsw.server.model.cards.objective.publics.PublicObjectiveCardsDeck;
import it.polimi.ingsw.server.model.cards.tool.ToolCardsDeck;
import it.polimi.ingsw.server.model.die.containers.DiceDraftPool;
import it.polimi.ingsw.server.model.die.containers.RoundTrack;
import it.polimi.ingsw.server.model.die.containers.WindowPatternCardDeck;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * This class represents everything which is visible by all players.
 */
public class CommonBoard {

    private static final int NUMBER_OF_ROUNDS = 10;

    /**
     * List of {@link Player}s connected to the match.
     */
    private final List<Player> players;


    /**
     * {@link it.polimi.ingsw.server.model.die.containers.DiceDraftPool} of the match.
     */
    private final DiceDraftPool draftPool;

    /**
     * {@link it.polimi.ingsw.server.model.die.containers.RoundTrack} of the match.
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
     * Deck composed by {@link it.polimi.ingsw.server.model.cards.objective.privates.PrivateObjectiveCard}.
     * @see PrivateObjectiveCardsDeck
     */
    private final PrivateObjectiveCardsDeck privateObjectiveCardsDeck;

    /**
     * Deck composed by {@link it.polimi.ingsw.server.model.cards.objective.publics.APublicObjectiveCard}.
     * @see PublicObjectiveCardsDeck
     */
    private final PublicObjectiveCardsDeck publicObjectiveCardsDeck;

    /**
     * Deck composed by {@link it.polimi.ingsw.server.model.die.containers.WindowPatternCard}.
     */
    private final WindowPatternCardDeck windowPatternCardDeck;

    /**
     * Deck composed by {@link it.polimi.ingsw.server.model.cards.tool.ToolCard}s.
     * @see ToolCardsDeck
     */
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

    private PublicObjectiveCardsDeck getPublicObjectiveCardsDeck() {
        return publicObjectiveCardsDeck;
    }

    private ToolCardsDeck getToolCardsDeck() {
        return toolCardsDeck;
    }

    /**
     * Retrieves a specific player within {@link #players}.
     * @param username name of the player to find.
     * @return the player whose name is the same as the one in input.
     */
    public Player getSpecificPlayer(String username) {
        for(Player p: players)
            if(username.equals(p.getPlayerName()))
                return p;
        throw new UnsupportedOperationException();
    }

    /**
     * This method is used to populate {@link ToolCardSlot}s with {@link it.polimi.ingsw.server.model.cards.tool.ToolCard}s,
     * drawn from the {@link ToolCardsDeck}.
     */
    private void populateToolSlots(){
        for (int i = 0; i < 3; i++) {
            try {
                this.getToolCardSlots().add(new ToolCardSlot(this.getToolCardsDeck().drawCard()));
                this.getToolCardSlots().get(i).setCost(1); //Default cost to use a tool card.
            } catch (EmptyException e) {
                SagradaLogger.log(Level.SEVERE, "Error during tool card drawing");
            }
        }
    }

    /**
     * This method is used to populate {@link PublicObjectiveCardSlot}s with {@link APublicObjectiveCard}s, drawn from
     * the {@link PublicObjectiveCardsDeck}.
     */
    private void populatePubObjSlots(){
        for (int i = 0; i < 3; i++) {
            try {
                this.getPublicObjectiveCardSlots().add(new PublicObjectiveCardSlot( (APublicObjectiveCard) this.getPublicObjectiveCardsDeck().drawCard()));
            } catch (EmptyException e) {
                SagradaLogger.log(Level.SEVERE, "Error during public objective card drawing");
            }
        }
    }

    /**
     * This method gives to each player a {@link PrivateObjectiveCard}, drawn from the {@link PrivateObjectiveCardsDeck}.
     */
    public void givePrivateObjCard(){
        for (Player p: this.players)
            try {
                p.setPrivateObjectiveCard( (PrivateObjectiveCard) this.privateObjectiveCardsDeck.drawCard());
            } catch (EmptyException e){
                SagradaLogger.log(Level.SEVERE, "Error during private objective card drawing");
            }
    }
}
