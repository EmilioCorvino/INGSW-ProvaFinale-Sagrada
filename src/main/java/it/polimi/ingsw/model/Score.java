package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PublicObjectiveCardSlot;
import it.polimi.ingsw.model.cards.objective.ObjectiveCardAnalyzerVisitor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;

/**
 * An implementation of {@link IScore}, this class contains scores computed from
 * {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard},
 * {@link it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard}
 * and favor tokens. It also calculates the points lost for cells left empty in the window pattern card.
 */
public class Score implements IScore {

    /**
     * Player owning the score.
     */
    private Player player;

    /**
     * Points gained from {@link it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard}.
     */
    private int publicObjectivePoints;

    /**
     * Points gained from {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard}.
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

    /**
     * Final score of the player, calculated in the end game phase.
     */
    private int totalScore;

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

    public int getTotalScore() {
        return totalScore;
    }

    private void setPublicObjectivePoints(int publicObjectivePoints) {
        this.publicObjectivePoints = publicObjectivePoints;
    }

    private void setPrivateObjectivePoints(int privateObjectivePoints) {
        this.privateObjectivePoints = privateObjectivePoints;
    }

    private void setFavorTokensPoints(int favorTokensPoints) {
        this.favorTokensPoints = favorTokensPoints;
    }

    private void setLostPoints(int lostPoints) {
        this.lostPoints = lostPoints;
    }

    private void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * This methods adds up the points earned from each
     * {@link it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard} in play and sets
     * publicObjectivePoints to this value.
     */
    private void computePublicObjectivePoints() {
        int pointsFromPublicObjectiveCards = 0;
        ObjectiveCardAnalyzerVisitor visitor = new ObjectiveCardAnalyzerVisitor();
        for(PublicObjectiveCardSlot cardSlot: this.getPlayer().getBoard().getPublicObjectiveCardSlots()) {
            cardSlot.getPublicObjectiveCard().accept(visitor, this.getPlayer().getWindowPatternCard());
            pointsFromPublicObjectiveCards += visitor.getScoreFromCard();
        }
        this.setPublicObjectivePoints(pointsFromPublicObjectiveCards);

    }

    /**
     * This methods computes the points earned from the player's
     * {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard} and sets privateObjectivePoints
     * to this value.
     */
    private void computePrivateObjectivePoints() {
        ObjectiveCardAnalyzerVisitor visitor = new ObjectiveCardAnalyzerVisitor();
        this.getPlayer().getPrivateObjectiveCard().accept(visitor, this.getPlayer().getWindowPatternCard());
        this.setPrivateObjectivePoints(visitor.getScoreFromCard());
    }

    /**
     * This methods sets the points taken from favor tokens to the number of tokens still available to the player.
     */
    private void computeFavorTokensPoints() {
        this.setFavorTokensPoints(this.getPlayer().getFavorTokens());
    }

    /**
     * This method checks how many empty cells are present in the player's window pattern card and sets lostPoints
     * to the number of empty cells.
     */
    private void computeLostPoints() {
        int emptyCells = 0;
        for(int i = 0; i < WindowPatternCard.getMaxRow(); i++) {
            for(int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if(this.getPlayer().getWindowPatternCard().getGlassWindow()[i][j].isEmpty()) {
                    emptyCells++;
                }
            }
        }
        this.setLostPoints(emptyCells);
    }

    /**
     * Adds up the scores from the various sources, produces the final score and sets it.
     */
    @Override
    public void computeTotalScore() {
        this.computePublicObjectivePoints();
        this.computePrivateObjectivePoints();
        this.computeFavorTokensPoints();
        this.computeLostPoints();

        this.setTotalScore(this.getPrivateObjectivePoints() + this.getPublicObjectivePoints() +
            this.getFavorTokensPoints() - this.getLostPoints());
    }
}
