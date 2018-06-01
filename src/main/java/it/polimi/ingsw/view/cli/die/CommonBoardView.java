package it.polimi.ingsw.view.cli.die;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class contains all the view object common for each player.
 */
public class CommonBoardView {

    private List<PlayerView> players;

    private DieDraftPoolView draftPool;

    private RoundTrackView roundTrack;

    //Forse basta una lista
    private Map<Integer,String> publicObjectiveCards;

    //Forse basta una lista
    private Map<Integer,String> toolCards;

    public CommonBoardView(){
        this.players = new ArrayList<>();
        this.roundTrack = new RoundTrackView();
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

    public Map<Integer,String> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public Map<Integer,String> getToolCards() {
        return toolCards;
    }

    public void setDraftPool(DieDraftPoolView draftPool) {
        this.draftPool = draftPool;
    }

}
