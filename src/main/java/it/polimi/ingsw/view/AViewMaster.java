package it.polimi.ingsw.view;

import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.network.IFromClientToServer;

import java.util.List;

public abstract class AViewMaster {

    public abstract void createConnection();

    public abstract IFromClientToServer chooseNetworkInterface(String ip);

    public abstract void showRoom(List<String> players);

    public abstract void showInitializedBoard(CommonBoard board);

    public abstract void showRank(String[] players, int[] score);

    public abstract void showAvailablePlaces(List<Integer> idDice);
}
