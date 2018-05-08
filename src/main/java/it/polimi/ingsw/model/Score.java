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


    public void setPublicObjectivePoints(int publicObjectivePoints) {
        this.publicObjectivePoints = publicObjectivePoints;
    }

    public void setPrivateObjectivePoints(int privateObjectivePoints) {
        this.privateObjectivePoints = privateObjectivePoints;
    }

    /**
     * This methods sets the points taken from favor tokens to the number of tokens still available to the player.
     */
    public void setFavorTokensPoints() {
        this.favorTokensPoints = this.getPlayer().getFavorTokens();
    }

    /**
     * This method checks how many empty cells are present in the player's window pattern card and sets lostPoints
     * to the number of empty cells.
     */
    public void setLostPoints() {
        //I assume glass windows are always 4x5
        //todo methods to get rows or columns might be useful.
        int lostPoints = 0;
        Cell[][] glassWindow = this.getPlayer().getWindowPatternCard().getGlassWindow();
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
     * @return the final player's score.
     */
    @Override
    public int computeFinalScore() {
        return this.getPlayer().getScore().getPublicObjectivePoints() +
                this.getPlayer().getScore().getPrivateObjectivePoints() +
                this.getPlayer().getScore().getFavorTokensPoints() - this.getPlayer().getScore().getLostPoints();
    }
}
