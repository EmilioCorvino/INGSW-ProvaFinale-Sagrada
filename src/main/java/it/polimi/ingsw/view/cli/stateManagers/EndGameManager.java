package it.polimi.ingsw.view.cli.stateManagers;


import it.polimi.ingsw.view.cli.boardElements.PlayerView;
import it.polimi.ingsw.view.cli.generalManagers.InputOutputManager;

import java.io.Serializable;

/**
 * This class control the end game state and all the respective interaction with the user.
 */
public class EndGameManager implements Serializable {

    private static final String BOLD_PREFIX = "\033[0m;1m";

    private static final String BOLD_SUFFIX = "\033[0m";

    private transient InputOutputManager inputOutputManager;

    public EndGameManager(InputOutputManager inputOutputManager){
        this.inputOutputManager = inputOutputManager;
    }

    public void showRank(String[] players, int[] score, PlayerView player){
        inputOutputManager.print(createClassification(players,score,player));
    }

    /**
     * This method convert the rank to a string, making bold the line of the player connected.
     * @param players: the list of userNames.
     * @param score: the list of scores
     * @param player: the player connected
     * @return a string that represents the rank.
     */
    private String createClassification(String[] players, int[] score, PlayerView player){
        StringBuilder rank = new StringBuilder("\nCLASSIFICA:\n");

        for(int i = 0; i < players.length; i++) {
            if (players[i].equals(player.getUserName()))
                rank.append(BOLD_PREFIX).append(i + 1).append(": ").append(players[i]).append(" ").append(score[i]).append(BOLD_SUFFIX);
            rank.append(i + 1).append(": ").append(players[i]).append(" ").append(score[i]);
        }

        return rank.toString();
    }
}
