package it.polimi.ingsw.view.cli.boardelements;

import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.die.RoundTrackView;

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


    public CommonBoardView(){
        this.players = new ArrayList<>();
        this.roundTrack = new RoundTrackView(10);
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
     */
    public void resetCommonBoard(){
        this.players = new ArrayList<>();
        this.roundTrack = new RoundTrackView(10);
        this.publicObjectiveCards = new ArrayList<>();
        this.toolCardViews = new ArrayList<>();
    }

    public String toolCardToString(){
        StringBuilder tool = new StringBuilder("\nCarte strumento: ");
        for (ToolCardView c : toolCardViews) {
            tool.append("\n\t - ").append(toolCardViews.indexOf(c)).append(": ").append(c.getDescription()).append(" | Segnalini favore da usare: ").append(c.getCost());
        }
        return tool.toString();
    }

    public String pubObjToString(){
        StringBuilder pubObj = new StringBuilder("\nCarte obiettivo pubblico: ");

        for (int i = 0; i < publicObjectiveCards.size(); i++)
            pubObj.append("\n\t - " ).append(i+1).append(": ").append(publicObjectiveCards.get(i));

        return pubObj.toString();
    }

    public String allWpToString(){
        StringBuilder allWp = new StringBuilder("\nMappe altri giocatori:\n");

        for (PlayerView p: players) {
            allWp.append("\n- Mappa di ").append(p.getUserName());
            allWp.append(p.getWp().wpToString());
        }

        return allWp.toString();
    }

}
