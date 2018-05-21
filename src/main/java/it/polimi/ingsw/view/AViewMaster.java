package it.polimi.ingsw.view;

import java.util.List;

public abstract class AViewMaster {

    public abstract void createConnection(AViewMaster viewMaster);

    public abstract void showRoom(List<String> players);
}
