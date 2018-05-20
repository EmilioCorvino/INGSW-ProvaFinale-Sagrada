package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PublicObjectiveCardSlot;
import it.polimi.ingsw.model.cards.ToolCardSlot;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;
import it.polimi.ingsw.model.die.diecontainers.RoundTrack;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents everything which is visible by all players.
 */
//todo This will probably be the observed class that will notify changes.
public class CommonBoard {

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

    public CommonBoard() {
        this.players = new ArrayList<>();
        this.draftPool = new DiceDraftPool();
        this.roundTrack = new RoundTrack(NUMBER_OF_ROUNDS);
        this.publicObjectiveCardSlots = new ArrayList<>();
        this.toolCardSlots = new ArrayList<>();
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
}
