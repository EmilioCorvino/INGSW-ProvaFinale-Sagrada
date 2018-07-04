package it.polimi.ingsw.view.cli.managers.general;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.utils.SagradaLogger;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.view.IToolCardManager;
import it.polimi.ingsw.view.cli.CliCommunicationManager;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.cli.boardelements.ToolCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CliToolCardCardManager extends CliCommunicationManager implements IToolCardManager {

    public CliToolCardCardManager(CliView view){
        super(view);
    }

    @Override
    public void tool1(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL1);
        this.printToolDescription(toolSlot);

        super.inputOutputManager.print(super.view.getCommonBoard().getDraftPool().diceDraftToString());
        super.inputOutputManager.print(super.view.getPlayer().getWp().wpToString());

        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseDraftDie(super.view.getCommonBoard().getDraftPool()));

        String option = super.inputOutputManager.askInformation("Inserire l'operazione desiderata (incrementa/decrementa):\n" +
                "\t- Incrementa di 1\n\t- Decrementa di 1");
        while (!("incrementa".equalsIgnoreCase(option) || "decrementa".equalsIgnoreCase(option)))
            option = super.inputOutputManager.askInformation("Errore: scelta non supportata, inserire (Decrementa/Incrementa): ");
        if ("incrementa".equalsIgnoreCase(option))
            infoUnit.setExtraParam(0);
        else
            infoUnit.setExtraParam(1);

        infoUnit.setDestinationIndex(super.view.getGamePlayManager().choseCellWp());

        units.add(infoUnit);


        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 1");
            disconnect();
        }

    }

    @Override
    public void tool2(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL2);
        this.printToolDescription(toolSlot);



        super.inputOutputManager.print(super.view.getPlayer().getWp().wpToString());
        this.fromWpToWp(infoUnit);

        units.add(infoUnit);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 2");
            disconnect();
        }

    }

    @Override
    public void tool3(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL3);
        this.printToolDescription(toolSlot);


        super.inputOutputManager.print(super.view.getPlayer().getWp().wpToString());
        this.fromWpToWp(infoUnit);

        units.add(infoUnit);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 3");
            disconnect();
        }

    }


    @Override
    public void tool4(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit1 = new SetUpInformationUnit();
        SetUpInformationUnit infoUnit2 = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL4);
        this.printToolDescription(toolSlot);


        super.inputOutputManager.print(super.view.getPlayer().getWp().wpToString());
        this.fromWpToWp(infoUnit1);
        this.fromWpToWp(infoUnit2);

        units.add(infoUnit1);
        units.add(infoUnit2);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 4");
            disconnect();
        }

    }

    @Override
    public void tool5(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL5);
        this.printToolDescription(toolSlot);

        super.inputOutputManager.print(super.view.getPlayer().getWp().wpToString());

        super.inputOutputManager.print(super.view.getCommonBoard().getDraftPool().diceDraftToString());
        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseDraftDie(super.view.getCommonBoard().getDraftPool()));

        super.inputOutputManager.print(super.view.getCommonBoard().getRoundTrack().roundTrackToString());
        super.view.getGamePlayManager().choseRoundDie(super.view.getCommonBoard().getRoundTrack(), infoUnit);

        infoUnit.setDestinationIndex(super.view.getGamePlayManager().choseCellWp());

        units.add(infoUnit);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 5");
            disconnect();
        }

    }

    @Override
    public void tool6(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL6);
        this.printToolDescription(toolSlot);

        super.inputOutputManager.print(super.view.getCommonBoard().getDraftPool().diceDraftToString());
        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseDraftDie(super.view.getCommonBoard().getDraftPool()));

        units.add(infoUnit);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 6");
            disconnect();
        }

    }


    @Override
    public void tool6Extra(){
        SetUpInformationUnit infoUnit = super.view.getGamePlayManager().getExtraInfo();

        super.inputOutputManager.print("ATTENZIONE: Se non puoi piazzarlo in nessun cella, scegline una errata.");
        super.inputOutputManager.print(super.view.getPlayer().getWp().wpToString());
        infoUnit.setDestinationIndex(super.view.getGamePlayManager().choseCellWp());

        try{
            server.performRestrictedPlacement(infoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 6");
            disconnect();
        }
    }

    @Override
    public void tool7(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL7);
        this.printToolDescription(toolSlot);

        units.add(infoUnit);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 7");
            disconnect();
        }

    }

    @Override
    public void tool8(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL8);
        this.printToolDescription(toolSlot);

        super.view.getGamePlayManager().getPlacementInfo(super.view.getCommonBoard().getDraftPool(), super.view.getPlayer().getWp(), infoUnit);

        units.add(infoUnit);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 8");
            disconnect();
        }

    }

    @Override
    public void tool9(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL9);
        this.printToolDescription(toolSlot);

        super.view.getGamePlayManager().getPlacementInfo(super.view.getCommonBoard().getDraftPool(), super.view.getPlayer().getWp(), infoUnit);

        units.add(infoUnit);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 9");
            disconnect();
        }

    }

    @Override
    public void tool10(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL10);
        this.printToolDescription(toolSlot);

        super.view.getGamePlayManager().getPlacementInfo(super.view.getCommonBoard().getDraftPool(), super.view.getPlayer().getWp(), infoUnit);

        units.add(infoUnit);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 10");
            disconnect();
        }

    }

    @Override
    public void tool11() {
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL11);
        this.printToolDescription(toolSlot);

        super.inputOutputManager.print(super.view.getCommonBoard().getDraftPool().diceDraftToString());
        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseDraftDie(super.view.getCommonBoard().getDraftPool()));

        units.add(infoUnit);

        try {
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 11");
            disconnect();
        }

    }


    @Override
    public void tool11Extra(){
        SetUpInformationUnit infoUnit = super.view.getGamePlayManager().getExtraInfo();

        int value = super.inputOutputManager.askInt("Scegli valore tra (1-6): ");

        while(value < 1 || value >6){
            value = super.inputOutputManager.askInt("Errore: inserire numero tra (1-6): ");
        }

        infoUnit.setValue(value);

        super.inputOutputManager.print("ATTENZIONE: Se non puoi piazzarlo in nessun cella, scegline una errata.");
        super.inputOutputManager.print(super.view.getPlayer().getWp().wpToString());
        infoUnit.setDestinationIndex(super.view.getGamePlayManager().choseCellWp());

        try{
            server.performRestrictedPlacement(infoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 11");
            disconnect();
        }
    }

    @Override
    public void tool12() {
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit1 = new SetUpInformationUnit();
        SetUpInformationUnit infoUnit2 = new SetUpInformationUnit();
        int nDice;

        do {
            nDice = super.inputOutputManager.askInt("Inserisci quanti dadi vuoi piazzare(1-2): ");
        } while (!(nDice == 1 || nDice == 2));


        int toolSlot = this.getSlotId(Commands.TOOL12);
        this.printToolDescription(toolSlot);

        super.inputOutputManager.print(super.view.getCommonBoard().getRoundTrack().roundTrackToString());
        super.view.getGamePlayManager().choseRoundDie(super.view.getCommonBoard().getRoundTrack(), infoUnit1);


        super.inputOutputManager.print(super.view.getPlayer().getWp().wpToString());
        this.fromWpToWp(infoUnit1);
        units.add(infoUnit1);

        if(nDice == 2) {
            infoUnit2.setExtraParam(infoUnit1.getExtraParam());
            infoUnit2.setOffset(infoUnit1.getOffset());
            this.fromWpToWp(infoUnit2);
            units.add(infoUnit2);
        }

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 12");
            disconnect();
        }

    }

    private int getSlotId(Commands command){
        int idSlot = -1;
        List<ToolCardView> cards = super.view.getCommonBoard().getToolCardViews();

        for (ToolCardView tool : cards)
            if (tool.getCommand().equals(command))
                return cards.indexOf(tool);
        return idSlot;
    }

    private void printToolDescription(int id){
        if (id > -1 && id < 3)
            super.inputOutputManager.print("Descrizione tool:\n\t- "+super.view.getCommonBoard().getToolCardViews().get(id).getDescription());
    }



    private void fromWpToWp(SetUpInformationUnit infoUnit){

        super.inputOutputManager.print("Da:");
        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseCellWp());

        super.inputOutputManager.print("A:");
        infoUnit.setDestinationIndex(super.view.getGamePlayManager().choseCellWp());
    }
}
