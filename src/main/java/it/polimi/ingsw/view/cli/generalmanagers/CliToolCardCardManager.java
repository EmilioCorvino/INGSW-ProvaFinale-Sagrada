package it.polimi.ingsw.view.cli.generalmanagers;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.cli.CliCommunicationManager;
import it.polimi.ingsw.view.IToolCardManager;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.cli.boardElements.CommonBoardView;
import it.polimi.ingsw.view.cli.boardElements.ToolCardView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CliToolCardCardManager extends CliCommunicationManager implements IToolCardManager {

    /**
     * An instance of the common board.
     */
    private CommonBoardView commonBoard;

    /**
     * An instance of the wp of the player connected.
     */
    private WindowPatternCardView wpPlayer;

    public CliToolCardCardManager(CliView view){
        super(view);
        this.commonBoard = super.view.getCommonBoard();
        this.wpPlayer = super.view.getPlayer().getWp();
    }

    @Override
    public void tool1(){
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL1);
        this.printToolDescription(toolSlot);

        super.inputOutputManager.print(this.commonBoard.getDraftPool().diceDraftToString());
        super.inputOutputManager.print(this.wpPlayer.wpToString());

        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseDraftDie(commonBoard.getDraftPool()));

        String option = super.inputOutputManager.askInformation("Inserire l'operazione desiderata:\n" +
                "\t- Incrementa di 1\n\t- Decrementa di 1");
        while (!(option.equalsIgnoreCase("incrementa") || option.equalsIgnoreCase("decrementa")))
            option = super.inputOutputManager.askInformation("Errore: scelta non supportata, inserire (Decrementa/Incrementa): ");
        if (option.equalsIgnoreCase("incrementa"))
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

        super.inputOutputManager.print(commonBoard.getDraftPool().diceDraftToString());
        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseDraftDie(commonBoard.getDraftPool()));

        super.inputOutputManager.print(commonBoard.getRoundTrack().roundTrackToString());
        super.view.getGamePlayManager().choseRoundDie(this.commonBoard.getRoundTrack(), infoUnit);

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

        super.inputOutputManager.print(this.commonBoard.getDraftPool().diceDraftToString());
        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseDraftDie(commonBoard.getDraftPool()));

        units.add(infoUnit);

        try{
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 6");
        }

    }

    //todo
    public void tool6Extra(){}

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

        super.view.getGamePlayManager().getPlacementInfo(this.commonBoard.getDraftPool(), wpPlayer, infoUnit);

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

        super.view.getGamePlayManager().getPlacementInfo(this.commonBoard.getDraftPool(), wpPlayer, infoUnit);

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

        super.view.getGamePlayManager().getPlacementInfo(this.commonBoard.getDraftPool(), wpPlayer, infoUnit);

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

        super.inputOutputManager.print(this.commonBoard.getDraftPool().diceDraftToString());
        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseDraftDie(commonBoard.getDraftPool()));

        units.add(infoUnit);

        try {
            super.server.performToolCardMove(toolSlot, units);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 11");
        }

    }

    //todo
    public void tool11Extra(){}

    @Override
    public void tool12() {
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit1 = new SetUpInformationUnit();
        SetUpInformationUnit infoUnit2 = new SetUpInformationUnit();
        String nDice = "0";

        while (!nDice.equals("1") || !nDice.equals("2"))
            nDice = super.inputOutputManager.askInformation("Inserisci quanti dadi vuoi piazzare(1-2): ");


        int toolSlot = this.getSlotId(Commands.TOOL12);
        this.printToolDescription(toolSlot);

        super.inputOutputManager.print(commonBoard.getRoundTrack().roundTrackToString());
        super.view.getGamePlayManager().choseRoundDie(this.commonBoard.getRoundTrack(), infoUnit1);

        this.fromWpToWp(infoUnit1);
        units.add(infoUnit1);

        if(nDice.equals("2")) {
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
            super.inputOutputManager.print("Descrizione tool:\n\t"+this.commonBoard.getToolCardViews().get(id).getDescription());
    }



    private void fromWpToWp(SetUpInformationUnit infoUnit){
        super.inputOutputManager.print(this.wpPlayer.wpToString());

        super.inputOutputManager.print("Da:");
        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseCellWp());

        super.inputOutputManager.print("A:");
        infoUnit.setDestinationIndex(super.view.getGamePlayManager().choseCellWp());
    }
}
