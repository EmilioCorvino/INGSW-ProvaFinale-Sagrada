package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.network.IFromServerToClient;

import java.util.List;

/**
 * This class handles messages arriving from the server, calling methods from {@link AViewMaster}.
 */
public class ClientImplementation implements IFromServerToClient {

    /**
     * View chosen by the user.
     */
    private AViewMaster view;

    public ClientImplementation(AViewMaster view) {
        this.view = view;
    }

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     */
    @Override
    public void showRoom(List<String> players) {
        view.showRoom(players);
    }

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {
        view.showMapsToChoose(listWp);
    }
    @Override
    public void showCommonBoard(DiceDraftPool draft, SimplifiedWindowPatternCard wp) {
        view.showCommonBoard(draft, wp);
    }

    @Override
    public void showCommand() {
        view.showCommand();
    }
    @Override
    public void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) {
        view.giveProperObjectToFill(setInfoUnit);
    }

    @Override
    public void showUpdatedWp(String username, SetUpInformationUnit info) {
        view.showUpdatedWp(username, info);
    }

    @Override
    public void showNotice(String notice){ view.showNotice(notice);}

}
