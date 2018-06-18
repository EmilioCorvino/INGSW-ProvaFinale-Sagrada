package it.polimi.ingsw.view.cli.boardElements;

import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.die.RoundTrackView;
import it.polimi.ingsw.view.cli.generalManagers.InputOutputManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the view object common for each player.
 */
public class CommonBoardView {

    private List<PlayerView> players;

    private DieDraftPoolView draftPool;

    private RoundTrackView roundTrack;

    private List<String> publicObjectiveCards;

    private List<ToolCardView> toolCardViews;


    public CommonBoardView(InputOutputManager inputOutputManager){
        this.players = new ArrayList<>();
        this.roundTrack = new RoundTrackView(10, inputOutputManager);
        this.publicObjectiveCards = new ArrayList<>();
        this.toolCardViews = new ArrayList<>();
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

    public List<ToolCardView> getToolCardViews() {
        return toolCardViews;
    }

    public void setDraftPool(DieDraftPoolView draftPool) {
        this.draftPool = draftPool;
    }

    /**
     * This method is used to reset all the attribute of the common board, it is used instead the constructor after a new game request.
     * @param inputOutputManager: The manager for the scan and the print to the user.
     */
    public void resetCommonBoard(InputOutputManager inputOutputManager){
        this.players = new ArrayList<>();
        this.roundTrack = new RoundTrackView(10, inputOutputManager);
        this.publicObjectiveCards = new ArrayList<>();
        this.toolCardViews = new ArrayList<>();
    }
}
