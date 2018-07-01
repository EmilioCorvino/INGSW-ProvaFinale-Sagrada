package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.IToolCardManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class GUIToolCardManager implements IToolCardManager {

    private IFromClientToServer server;

    private PlayersData playersData;

    private GUIView view;

    public GUIToolCardManager(GUIView view ) {
        this.view = view;
    }

    @Override
    public void tool1() {
        List<SetUpInformationUnit> setupList = new ArrayList<>();
        setupList.add(playersData.getSetUpInformationUnit());
        System.out.println("id slot: " + playersData.getSlotChosen());
        try{
            this.server.performToolCardMove(playersData.getSlotChosen(), setupList);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 1");
            //disconnect();
        }


    }

    @Override
    public void tool2() {

    }

    @Override
    public void tool3() {

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
}
