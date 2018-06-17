package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.view.cli.generalManagers.InputOutputManager;

import java.io.Serializable;

/**
 * This class control the end game state and all the respective interaction with the user.
 */
public class EndGameManager implements Serializable {

    private InputOutputManager inputOutputManager;

    public EndGameManager(InputOutputManager inputOutputManager){
        this.inputOutputManager = inputOutputManager;
    }

    public void showRank(String[] players, int[] score){

    }

    public String createClassification(String[] players, int[] score){
        StringBuilder rank = new StringBuilder("\nCLASSIFICA:\n");

        for(int i = 0; i < players.length; i++)
            rank.append(players[i] + score[i]);

        return rank.toString();
    }
}
