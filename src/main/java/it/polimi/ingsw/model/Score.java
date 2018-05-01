package it.polimi.ingsw.model;

/**
 * An implementation of IScore, this class contains scores computed from private objective cards, public objective cards
 * and favor tokens. It also calculates the points lost for cells left empty in the window pattern card.
 */
public class Score implements IScore {

    /**
     * Player owning the score.
     */
    private Player player;

    /**
     * Points gained from public objective cards.
     */
    private int publicObjectivePoints;

    /**
     * Points gained from private objective cards.
     */
    private int privateObjectivePoints;

    /**
     * Points gained from favor tokens.
     */
    private int favorTokensPoints;

    /**
     * Points lost for leaving cells in the window pattern card empty.
     */
    private int lostPoints;

    public Score(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPublicObjectivePoints() {
        return publicObjectivePoints;
    }

    public int getPrivateObjectivePoints() {
        return privateObjectivePoints;
    }

    public int getFavorTokensPoints() {
        return favorTokensPoints;
    }

    public int getLostPoints() {
        return lostPoints;
    }

    /*
     * The following set methods will be used by computeFinalScore to fill this class.
     */

    public void setPublicObjectivePoints(int publicObjectivePoints) {
        this.publicObjectivePoints = publicObjectivePoints;
    }

    public void setPrivateObjectivePoints(int privateObjectivePoints) {
        this.privateObjectivePoints = privateObjectivePoints;
    }

    public void setFavorTokensPoints(int favorTokensPoints) {
        this.favorTokensPoints = favorTokensPoints;
    }

    /**
     * This method checks how many empty cells are present in the player's window pattern card and sets lostPoints
     * to the number of empty cells.
     * @param player whose window pattern card is to be checked.
     */
    public void setLostPoints(Player player) {
        //todo I assume glass windows are always 4x5, should it be otherwise?
        //todo methods to get rows or columns mught be useful.
        int lostPoints = 0;
        Cell[][] glassWindow = player.getWindowPatternCard().getGlassWindow();
        for(int i = 0; i < 4; i++) { //rows
            for(int j = 0; j < 5; j++) { //columns
                if(glassWindow[i][j].isEmpty()) {
                    lostPoints++;
                }
            }
        }
        this.lostPoints = lostPoints;
    }

    /**
     * Adds up the scores from the various sources and produces the final score.
     * @param player whose score is to be computed.
     * @return the final player's score.
     */
    @Override
    public int computeFinalScore(Player player) {
        return player.getScore().getPublicObjectivePoints() + player.getScore().getPrivateObjectivePoints() +
                player.getScore().getFavorTokensPoints() - player.getScore().getLostPoints();
    }
}
