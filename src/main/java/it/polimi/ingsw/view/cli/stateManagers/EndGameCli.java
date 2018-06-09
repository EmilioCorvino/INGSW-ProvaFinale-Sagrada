package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.view.cli.InputOutputManager;

import java.io.Serializable;

/**
 * This class control the end game state and all the respective interaction with the user.
 */
public class EndGameCli implements Serializable {

    private InputOutputManager inputOutputManager;

    public EndGameCli(InputOutputManager inputOutputManager){
        this.inputOutputManager = inputOutputManager;
    }

    public void showRank(String[] players, int[] score){};
}
