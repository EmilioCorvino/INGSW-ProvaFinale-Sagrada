package it.polimi.ingsw.view.cli.die;

import java.util.Map;

public class CommonBoardView {

    Map<String, WindowPatternCardView> players;

    DieDraftPoolView draftPool;

    RoundTrackView roundTrack;

    public Map<String, WindowPatternCardView> getPlayers() {
        return players;
    }

    public DieDraftPoolView getDraftPool() {
        return draftPool;
    }

    public RoundTrackView getRoundTrack() {
        return roundTrack;
    }
}
