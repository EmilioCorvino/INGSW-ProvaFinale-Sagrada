package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PublicObjectiveCardSlot;
import it.polimi.ingsw.model.cards.ToolCardSlot;
import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCardsDeck;
import it.polimi.ingsw.model.cards.objective.publics.PublicObjectiveCardsDeck;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;
import it.polimi.ingsw.model.die.diecontainers.RoundTrack;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCardDeck;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

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
     * {@link it.polimi.ingsw.model.die.diecontainers.DiceDraftPool} of the match.
     */
    private final DiceDraftPool draftPool;

    /**
     * {@link it.polimi.ingsw.model.die.diecontainers.RoundTrack} of the match.
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
     * Deck composed by {@link it.polimi.ingsw.model.die.diecontainers.WindowPatternCard}.
     */
    private final WindowPatternCardDeck windowPatternCardDeck;

    /*todo private final ToolCardsDeck toolCardsDeck */

    public CommonBoard() {
        this.players = new ArrayList<>();
        this.draftPool = new DiceDraftPool();
        this.roundTrack = new RoundTrack(NUMBER_OF_ROUNDS);
        this.publicObjectiveCardSlots = new ArrayList<>();
        this.toolCardSlots = new ArrayList<>();
        this.privateObjectiveCardsDeck = new PrivateObjectiveCardsDeck();
        this.privateObjectiveCardsDeck.parseDeck();
        this.publicObjectiveCardsDeck = new PublicObjectiveCardsDeck();
        this.publicObjectiveCardsDeck.parseDeck();
        this.windowPatternCardDeck = new WindowPatternCardDeck();
        this.windowPatternCardDeck.parseDeck();
        //todo the same with tool cards deck.
    }

    /*todo This method will update the Window pattern of a specific player.
    public void triggerNormalPlacement(Player player, Die die){
        draftPool.update(die);
        for(Player p : players)
            if (p.isSamePlayerAs(player)) {
                p.getWindowPatternCard().update(die);
                this.notifyController();
            }
    }
    */

    /*todo This method will update the Window pattern of a specific player.
    public void triggerWpPlacement(Die die, Player player){
        for( Player p : players)
            if (player.getPlayerName() == p.getPlayerName()){
                p.getWindowPatternCard().update(die);
                this.notifyController();
            }

    }
    */

    /**
     * This method make the update of Round Track and trigger the notify to the controller
     * @param action: Type of modification, (update) if a die want to be added, (remove) if a die want to be removed.
     * @param die: the die to be added or removed from the round track.
     */
    /*todo this method will be update the Round Track and trigger the notify to the controller
    public void triggerRoundTrackPlacement(String action, Die die){
        if (action == "update")
            roundTrack.update(die);
        if (action == "remove")
            roundTrack.remove(die);
        this.notifyController();
    }
    */

    /**
     * This method update the draft pool.
     * @param die: The die to be added.
     */
    /*todo
    public void triggerDiceDraftPlacement(Die die){
        draftPool.update(die);
        this.notifyController();
    }
    */

    /**
     * This method notify the controller that something has been modify.
    */
    /*todo This method will notify the modification to the controller.
    public void notifyController(){
        controller.updateView();
    }
    */


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
}
