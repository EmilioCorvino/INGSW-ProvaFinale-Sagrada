package it.polimi.ingsw.client.view.gui.gamewindows;

import it.polimi.ingsw.client.view.IToolCardManager;
import it.polimi.ingsw.client.view.gui.GUIView;
import it.polimi.ingsw.client.view.gui.PlayersData;
import it.polimi.ingsw.common.network.IFromClientToServer;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;

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

    /**
     * This method sends the information to perform the effect of the tool number four;
     */
    @Override
    public void tool4() {
        List<SetUpInformationUnit> setupInfo = new ArrayList<>();
        List<Integer> values = this.playersData.getPersonalWp().getCellsClicked();

        SetUpInformationUnit infoOne = new SetUpInformationUnit();
        infoOne.setSourceIndex(values.get(0));
        infoOne.setDestinationIndex(values.get(1));

        SetUpInformationUnit infoTwo = new SetUpInformationUnit();
        infoTwo.setSourceIndex(values.get(2));
        infoTwo.setDestinationIndex(values.get(3));

        setupInfo.add(infoOne);
        setupInfo.add(infoTwo);

        try{
            this.server.performToolCardMove(playersData.getSlotChosen(), setupInfo);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 4");
            //disconnect();
        }
    }

    /**
     * This method sends the information to perform the effect of the tool number five;
     */
    @Override
    public void tool5() {
        List<SetUpInformationUnit> listInfo =  new ArrayList<>();
        SetUpInformationUnit setup = this.playersData.getSetUpInformationUnit();

        setup.setSourceIndex(this.view.getCommonWindow().getDraftPoolGUI().getIndexChosenCell());
        setup.setDestinationIndex(this.playersData.getPersonalWp().getClicked());
        setup.setExtraParam(this.view.getCommonWindow().getRoundTrack().getRound());
        setup.setOffset(this.view.getCommonWindow().getRoundTrack().getOffset());

        listInfo.add(setup);

        try{
            this.server.performToolCardMove(playersData.getSlotChosen(), listInfo);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 4");
            //disconnect();
        }
    }

    /**
     * This method sends the information to perform the effect of the tool number six;
     */
    @Override
    public void tool6() {
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        infoUnit.setSourceIndex(this.view.getCommonWindow().getDraftPoolGUI().getIndexChosenCell());

        units.add(infoUnit);

        try{
            this.server.performToolCardMove(playersData.getSlotChosen(), units);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 6");
            //disconnect();
        }
    }

    /**
     * This method sends the information to perform the effect of the tool number six extra;
     */
    @Override
    public void tool6Extra() {

        SetUpInformationUnit infoUnit = this.playersData.getSetUpInformationUnit();
        infoUnit.setDestinationIndex(this.playersData.getPersonalWp().getClicked());

        if(!this.playersData.getPersonalWp().isWpCellClicked()) {
            this.view.getCommonWindow().sendMessage("Non hai scelto la cella!");
            return;
        }

        try{
            server.performRestrictedPlacement(infoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 6");
            //disconnect();
        }

    }

    /**
     * This method sends the information to perform the effect of the tool number seven;
     */
    @Override
    public void tool7() {
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        units.add(infoUnit);

        try{
            this.server.performToolCardMove(playersData.getSlotChosen(), units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 7");
            //disconnect();
        }
    }

    /**
     * This method sends the information to perform the effect of the tool number eight;
     */
    @Override
    public void tool8() {
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = this.playersData.getSetUpInformationUnit();
        units.add(infoUnit);

        try{
            this.server.performToolCardMove(playersData.getSlotChosen(), units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 7");
            //disconnect();
        }
    }

    /**
     * This method sends the information to perform the effect of the tool number nine;
     */
    @Override
    public void tool9() {

        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = this.playersData.getSetUpInformationUnit();
        units.add(infoUnit);

        try{
            this.server.performToolCardMove(playersData.getSlotChosen(), units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 7");
            //disconnect();
        }
    }

    /**
     * This method sends the information to perform the effect of the tool number ten;
     */
    @Override
    public void tool10() {

        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = this.playersData.getSetUpInformationUnit();
        units.add(infoUnit);

        try{
            this.server.performToolCardMove(playersData.getSlotChosen(), units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 7");
            //disconnect();
        }

    }

    /**
     * This method sends the information to perform the effect of the tool number eleven;
     */
    @Override
    public void tool11() {
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = this.playersData.getSetUpInformationUnit();
        infoUnit.setSourceIndex(this.view.getCommonWindow().getDraftPoolGUI().getIndexChosenCell());

        units.add(infoUnit);

        try {
            this.server.performToolCardMove(this.playersData.getSlotChosen(), units);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 11");
            //disconnect();
        }
    }

    /**
     * This method sends the information to perform the effect of the tool number eleven extra;
     */
    @Override
    public void tool11Extra() {

        SetUpInformationUnit infoUnit = this.playersData.getSetUpInformationUnit();

        infoUnit.setDestinationIndex(this.playersData.getPersonalWp().getClicked());

        if(!this.playersData.getPersonalWp().isWpCellClicked()) {
            this.view.getCommonWindow().sendMessage("Non hai scelto la cella!");
            return;
        }

        try{
            server.performRestrictedPlacement(infoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 11");
            //disconnect();
        }
    }

    /**
     * This method sends the information to perform the effect of the tool number twelve;
     */
    @Override
    public void tool12() {
        List<SetUpInformationUnit> listinfo = new ArrayList<>();
        SetUpInformationUnit info1 = new SetUpInformationUnit();
        info1.setExtraParam(this.view.getCommonWindow().getRoundTrack().getRound());
        info1.setOffset(this.view.getCommonWindow().getRoundTrack().getOffset());
        info1.setSourceIndex(this.playersData.getPersonalWp().getCellsClicked().get(0));
        info1.setDestinationIndex(this.playersData.getPersonalWp().getCellsClicked().get(1));
        listinfo.add(info1);

        SetUpInformationUnit info2;

        if(this.playersData.getPersonalWp().getCellsClicked().size() == 4) {
           info2 = new SetUpInformationUnit();
           info2.setExtraParam(info1.getExtraParam());
           info2.setOffset(info1.getOffset());
           info2.setSourceIndex(this.playersData.getPersonalWp().getCellsClicked().get(2));
           info2.setDestinationIndex(this.playersData.getPersonalWp().getCellsClicked().get(3));
           listinfo.add(info2);
        }

        try{
            this.server.performToolCardMove(playersData.getSlotChosen(), listinfo);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 12");
            //disconnect();
        }

    }

    public IFromClientToServer getServer() {
        return server;
    }

    public void setServer(IFromClientToServer server) {
        this.server = server;
    }

    public void setPlayersData(PlayersData playersData) {
        this.playersData = playersData;
    }
}
