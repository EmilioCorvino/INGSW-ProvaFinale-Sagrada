package it.polimi.ingsw.view.cli.die;

import java.util.ArrayList;
import java.util.List;

public class CommonBoardView {

    private List<PlayerView> players;

    private DieDraftPoolView draftPool;

    private RoundTrackView roundTrack;

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

    public void setDraftPool(DieDraftPoolView draftPool) {
        this.draftPool = draftPool;
    }
}
