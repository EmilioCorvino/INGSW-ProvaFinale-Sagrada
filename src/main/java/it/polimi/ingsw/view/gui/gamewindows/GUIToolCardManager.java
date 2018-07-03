package it.polimi.ingsw.view.gui.gamewindows;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.SagradaLogger;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.view.IToolCardManager;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.PlayersData;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * This class manages the preparation of the information to send to the server to be elaborated to perform
 * the effect of the chosen tool card.
 */
public class GUIToolCardManager implements IToolCardManager {

    /**
     * The server where to send the information.
     */
    private IFromClientToServer server;

    /**
     * The main data container of the specific player where to pick all the needed data.
     */
    private PlayersData playersData;

    /**
     * The GUI view of the game.
     */
    private GUIView view;

    public GUIToolCardManager(GUIView view ) {
        this.view = view;
    }

    /**
     * This method collects and prepares all the needed information to send to the server to perform
     * the effecto of the tool card number one.
     */
    @Override
    public void tool1() {
        List<SetUpInformationUnit> setupList = new ArrayList<>();
        setupList.add(playersData.getSetUpInformationUnit());
        try{
            this.server.performToolCardMove(playersData.getSlotChosen(), setupList);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 1");
            //disconnect();
        }
    }

    /**
     * This method sends the information to perform the effect of the tool number two;
     */
    @Override
    public void tool2() {
        List<SetUpInformationUnit> setupList = new ArrayList<>();
        SetUpInformationUnit info = new SetUpInformationUnit();
        List<Integer> values = this.playersData.getPersonalWp().getCellsClicked();

        info.setSourceIndex(values.get(0));
        info.setDestinationIndex(values.get(1));

        setupList.add(info);

        try{
            this.server.performToolCardMove(playersData.getSlotChosen(), setupList);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 2");
            //disconnect();
        }
    }

    /**
     * This method sends the information to perform the effect of the tool number three;
     */
    @Override
    public void tool3() {
        tool2();
    }

    @Override
    public void tool4() {

    }

    @Override
    public void tool5() {

    }

    @Override
    public void tool6() {

    }

    @Override
    public void tool6Extra() {

    }

    @Override
    public void tool7() {

    }

    @Override
    public void tool8() {

    }

    @Override
    public void tool9() {

    }

    @Override
    public void tool10() {

    }

    @Override
    public void tool11() {

    }

    @Override
    public void tool11Extra() {

    }

    @Override
    public void tool12() {

    }

    public IFromClientToServer getServer() {
        return server;
    }

    public void setServer(IFromClientToServer server) {
        this.server = server;
    }

    public PlayersData getPlayersData() {
        return playersData;
    }

    public void setPlayersData(PlayersData playersData) {
        this.playersData = playersData;
    }
}
