package it.polimi.ingsw.view;

import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.network.IFromServerToClient;

import java.util.List;

public abstract class AViewMaster  {

    public abstract void createConnection(AViewMaster viewMaster);

    public abstract void showRoom(List<String> players);

    public abstract IFromClientToServer getServer();
}
