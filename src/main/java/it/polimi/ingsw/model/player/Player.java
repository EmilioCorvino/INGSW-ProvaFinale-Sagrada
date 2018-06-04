package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.IOController;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.Score;
import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.move.IMove;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the representation of the player: it does anything the real player can do and has everything
 * the real player has. It can see the board through the {@link CommonBoard}, make moves using {@link IMove} interface
 * and get his score computed by the {@link Score} class.
 */
public class Player implements IPlayer {

    /**
     * Favor tokens available to the player. They can be spent and give points in the end.
     */
    private int favorTokens;

    /**
     * Name player has chosen.
     */
    private final String playerName;

    /**
     * Private objective card assigned to the player. It is used for score computation.
     */
    private PrivateObjectiveCard privateObjectiveCard;

    /**
     * {@link WindowPatternCard} chosen by the player. It is set by chooseWindowPatternCard.
     */
    private WindowPatternCard windowPatternCard;

    /**
     * Represents everything the player can see (other players' WindowPatternCards, RoundTrack,
     * PublicObjectiveCards, DiceDraftPool).
     */
    private final CommonBoard board;

    /**
     * By this attribute everything concerning player's score and its computation can be known.
     */
    private final Score score;

    /**
     * This list contains all the moves made by the player.
     */
    private List<IMove> playerMoves;

    public Player(String playerName, CommonBoard board) {
        this.playerName = playerName;
        this.board = board;
        //board.getPlayers().add(this);
        this.score = new Score(this);
        this.playerMoves = new ArrayList<>();
    }



    public WindowPatternCard getWindowPatternCard() {
        return windowPatternCard;
    }

    /**
     * It's used to set both the player's window pattern card and the favor tokens available to him.
     * @param windowPatternCard card to link to the player.
     */
    public void setWindowPatternCard(WindowPatternCard windowPatternCard) {
        this.windowPatternCard = windowPatternCard;
        this.setFavorTokens(windowPatternCard.getDifficulty());
    }

    public CommonBoard getBoard() {
        return board;
    }

    public int getFavorTokens() {
        return favorTokens;
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
     * It is used by setWindowPatternCard to set the number of favor tokens available to the window pattern card
     * difficulty.
     * @param favorTokens number of favor tokens available to the player. It's equal to the WindowPatternCard difficulty.
     */
    private void setFavorTokens(int favorTokens) {
        this.favorTokens = favorTokens;
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    /**
     * Launches the execution of the move and adds it to the list of performed moves.
     * @param commonBoard board the player is watching.
     * @param ioController class containing the input inserted by the user.
     * @param move to be performed.
     */
    @Override
    public void performMove(CommonBoard commonBoard, IOController ioController, IMove move) {

        this.getPlayerMoves().add(move);
    }

    /**
     * Useful to check if the performer of an action is a certain player. It just compares players' names.
     * @param player subject to check.
     * @return true if the player is the same.
     */
    @Override
    public boolean isSamePlayerAs(Player player) {
        return this.getPlayerName().equals(player.getPlayerName());
    }

    @Override
    public String toString() {
        return "Name: " + this.getPlayerName() +
                "\n" + "Favor tokens available: " + this.getFavorTokens() +
                "\n" + "Window Pattern Card: " + this.getWindowPatternCard().toString();
    }
}
