package it.polimi.ingsw.view.cli.generalmanagers;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.cli.CliCommunicationManager;
import it.polimi.ingsw.view.IToolCardManager;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.cli.boardelements.ToolCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

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

        String option = super.inputOutputManager.askInformation("Inserire l'operazione desiderata:\n" +
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
        }

    }

    @Override
    public void tool2(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL2);
        this.printToolDescription(toolSlot);

        this.fromWpToWp(infoUnit);

        units.add(infoUnit);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 2");
        }

    }

    @Override
    public void tool3(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL3);
        this.printToolDescription(toolSlot);

        this.fromWpToWp(infoUnit);

        units.add(infoUnit);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 3");
        }

    }


    @Override
    public void tool4(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit1 = new SetUpInformationUnit();
        SetUpInformationUnit infoUnit2 = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL4);
        this.printToolDescription(toolSlot);

        this.fromWpToWp(infoUnit1);
        this.fromWpToWp(infoUnit2);

        units.add(infoUnit1);
        units.add(infoUnit2);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 4");
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
        }

    }


    @Override
    public void tool6Extra(){
        SetUpInformationUnit infoUnit = super.view.getGamePlayManager().getExtraInfo();

        super.inputOutputManager.print("ATTENZIONE: Se non puoi piazzarlo in nessun cella, scegline una errata.");
        super.inputOutputManager.print(super.view.getPlayer().getWp().wpToString());
        infoUnit.setDestinationIndex(super.view.getGamePlayManager().choseCellWp());

        /*todo, server method missing
        try{
            performRestrictedPlacement(SetUpInformationUnit infoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 6");
        }
        */
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
        }

    }


    @Override
    public void tool11Extra(){
        SetUpInformationUnit infoUnit = super.view.getGamePlayManager().getExtraInfo();

        String value = super.inputOutputManager.askInformation("Scegli valore tra (1-6): ");
        boolean validValue = Pattern.matches("\\d+", value);

        while(!validValue || Integer.parseInt(value) < 1 || Integer.parseInt(value) >6){
            value = super.inputOutputManager.askInformation("Errore: inserire numero tra (1-6): ");
            validValue = Pattern.matches("\\d+", value);
        }

        infoUnit.setValue(Integer.parseInt(value));

        super.inputOutputManager.print("ATTENZIONE: Se non puoi piazzarlo in nessun cella, scegline una errata.");
        super.inputOutputManager.print(super.view.getPlayer().getWp().wpToString());
        infoUnit.setDestinationIndex(super.view.getGamePlayManager().choseCellWp());

        /*
        try{
            performRestrictedPlacement(SetUpInformationUnit infoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 11");
        }
        */
    }

    @Override
    public void tool12() {
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit1 = new SetUpInformationUnit();
        SetUpInformationUnit infoUnit2 = new SetUpInformationUnit();
        String nDice = "0";

        while (!"1".equals(nDice) || !"2".equals(nDice))
            nDice = super.inputOutputManager.askInformation("Inserisci quanti dadi vuoi piazzare(1-2): ");


        int toolSlot = this.getSlotId(Commands.TOOL12);
        this.printToolDescription(toolSlot);

        super.inputOutputManager.print(super.view.getCommonBoard().getRoundTrack().roundTrackToString());
        super.view.getGamePlayManager().choseRoundDie(super.view.getCommonBoard().getRoundTrack(), infoUnit1);

        this.fromWpToWp(infoUnit1);
        units.add(infoUnit1);

        if("2".equals(nDice)) {
            infoUnit2.setExtraParam(infoUnit1.getExtraParam());
            infoUnit2.setOffset(infoUnit1.getOffset());
            this.fromWpToWp(infoUnit2);
            units.add(infoUnit2);
        }

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 12");
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
        if (id > 0 && id < 3)
            super.inputOutputManager.print("Descrizione tool:\n\t"+super.view.getCommonBoard().getToolCardViews().get(id).getDescription());
    }



    private void fromWpToWp(SetUpInformationUnit infoUnit){
        super.inputOutputManager.print(super.view.getPlayer().getWp().wpToString());

        super.inputOutputManager.print("Da:");
        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseCellWp());

        super.inputOutputManager.print("A:");
        infoUnit.setDestinationIndex(super.view.getGamePlayManager().choseCellWp());
    }
}
