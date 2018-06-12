package it.polimi.ingsw.view.cli.boardElements;

import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.die.RoundTrackView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains all the view object common for each player.
 */
public class CommonBoardView {

    private List<PlayerView> players;

    private DieDraftPoolView draftPool;

    private RoundTrackView roundTrack;

    private List<String> publicObjectiveCards;

    private List<ToolCard> toolCards;


    public CommonBoardView(){
        this.players = new ArrayList<>();
        this.roundTrack = new RoundTrackView();
        this.publicObjectiveCards = new ArrayList<>();
        this.toolCards = new ArrayList<>();
    }

    public List<PlayerView> getPlayers() {
        return players;
    }

    public DieDraftPoolView getDraftPool() {
        return draftPool;
    }

    public RoundTrackView getRoundTrack() {
        return roundTrack;
    }

    public List<String> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public List<ToolCard> getToolCards() {
        return toolCards;
    }

    public void setDraftPool(DieDraftPoolView draftPool) {
        this.draftPool = draftPool;
    }

}
