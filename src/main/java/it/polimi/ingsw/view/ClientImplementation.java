package it.polimi.ingsw.view;

import it.polimi.ingsw.exceptions.BrokenConnectionException;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.view.AViewMaster;

import java.util.List;

public class ClientImplementation implements IFromServerToClient {

    private AViewMaster view;

    public ClientImplementation(AViewMaster view) {
        this.view = view;
    }

    @Override
    public void showRoom(List<String> players) {
        view.showRoom(players);
    }
}
