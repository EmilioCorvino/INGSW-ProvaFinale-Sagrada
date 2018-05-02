package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.PrivateObjectiveCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements IPlayer {

    /**
     * Favor tokens available to the player. They can be spent and give points in the end.
     */
    private int favorTokens;

    /**
     * Name player has chosen.
     */
    private String playerName;

    /**
     * Final score of the player, calculated in the end game phase.
     */
    private int totalScore;

    /**
     * Private objective card assigned to the player. It is used for score computation.
     */
    private PrivateObjectiveCard privateObjectiveCard;

    /**
     * Window pattern cardWindowPatternCard chosen by the player. It is set by chooseWindowPatternCard.
     */
    private WindowPatternCard windowPatternCard;

    /**
     * Represents everything the player can see (other player's WindowPatternCards, RoundTrack,
     * PublicObjectiveCards, DiceDraftPool).
     */
    private CommonBoard board;

    /**
     * By this attribute everything concerning player's score and its computation can be known.
     */
    private Score score;

    /**
     * This list contains all the moves made by the player.
     */
    private List<IMove> playerMoves;

    public Player(String playerName, CommonBoard board) {
        this.playerName = playerName;
        this.board = board;
        this.score = new Score(this);
        this.playerMoves = new ArrayList<IMove>();
    }

    /**
     * Lets the player choose the window pattern card. Moreover, sets the favor tokens at the same number of the
     * window pattern card difficulty and links the window pattern card to the player throw the setWindowPatternCard method.
     * @param availableWindows list of the possible window pattern cards among which the player can choose.
     */
    @Override
    public void chooseWindowPatternCard(List<WindowPatternCard> availableWindows) {
        //todo get choice from the view
        Random r = new Random(4);
        WindowPatternCard playerCard = availableWindows.get(r.nextInt());
        setWindowPatternCard(playerCard);
        setFavorTokens(playerCard.getDifficulty());
    }

    public WindowPatternCard getWindowPatternCard() {
        return windowPatternCard;
    }

    /**
     * It's used by chooseWindowPatternCard to set the player's window pattern card.
     * @param windowPatternCard card to link to the player.
     */
    public void setWindowPatternCard(WindowPatternCard windowPatternCard) {
        this.windowPatternCard = windowPatternCard;
    }

    public CommonBoard getBoard() {
        return board;
    }

    public int getFavorTokens() {
        return favorTokens;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public List<IMove> getPlayerMoves() {
        return playerMoves;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public Score getScore() {
        return score;
    }

    public String getPlayerName() {
        return playerName;
    }

    /**
     * It is used by chooseWindowPatternCard to set the number of favor tokens available to the window pattern card
     * difficulty.
     * @param favorTokens number of favor tokens available to the player. It's equal to the WindowPatternCard difficulty.
     */
    public void setFavorTokens(int favorTokens) {
        this.favorTokens = favorTokens;
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    /**
     * This method is used by the Score class to set the final score of the player.
     * @param totalScore final score of the player.
     */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Useful to check if the performer of an action is a certain player. It just compares players' names.
     * @param player subject to check.
     * @return true if the player is the same.
     */
    public boolean equals(Player player) {
        return this.getPlayerName().equals(player.getPlayerName());
    }

    @Override
    public String toString() {
        return "Name: " + this.getPlayerName() +
                "\n" + "Favor tokens available: " + this.getFavorTokens() +
                "\n" + "Window Pattern Card: " + this.getWindowPatternCard().toString();
    }
}
