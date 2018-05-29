package it.polimi.ingsw.view.cli.die;

import java.util.List;

public class CommonBoardView {

    List<PlayerView> players;

    DieDraftPoolView draftPool;

    RoundTrackView roundTrack;

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
