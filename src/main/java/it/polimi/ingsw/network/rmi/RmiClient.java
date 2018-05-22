package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.IClient;
import it.polimi.ingsw.view.AViewMaster;

import java.io.Serializable;
import java.util.List;

public class RmiClient implements IClient {

    private final AViewMaster viewMaster;

    public RmiClient(AViewMaster viewMaster) {
        this.viewMaster = viewMaster;
    }

    @Override
    public void showRoom(List<String> players) {
        viewMaster.showRoom(players);
    }
}
