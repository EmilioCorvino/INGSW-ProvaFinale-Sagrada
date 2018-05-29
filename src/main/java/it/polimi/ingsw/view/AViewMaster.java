package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.network.IFromServerToClient;

import java.util.List;

public abstract class AViewMaster  {

    public abstract void createConnection(AViewMaster viewMaster);

    public abstract void showRoom(List<String> players);

    public abstract void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp);

    public abstract void showCommonBoard(DiceDraftPool draft, SimplifiedWindowPatternCard wp);

    public abstract void showCommand();

    public abstract void giveProperObjectToFill(SetUpInformationUnit setInfoUnit);

    public abstract IFromClientToServer getServer();
}
