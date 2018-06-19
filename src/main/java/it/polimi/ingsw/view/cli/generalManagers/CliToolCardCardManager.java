package it.polimi.ingsw.view.cli.generalManagers;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.cli.CliCommunicationManager;
import it.polimi.ingsw.view.IToolCardManager;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.cli.boardElements.CommonBoardView;
import it.polimi.ingsw.view.cli.boardElements.ToolCardView;

import java.util.List;
import java.util.logging.Level;

public class CliToolCardCardManager extends CliCommunicationManager implements IToolCardManager {

    /**
     * An instance of the common board.
     */
    private CommonBoardView commonBoard;

    public CliToolCardCardManager(CliView view){
        super(view);
        this.commonBoard = this.getView().getCommonBoard();
    }

    @Override
    public void tool1(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        infoUnit.setSourceIndex(this.getView().getGamePlayManager().choseDraftDie(commonBoard.getDraftPool()));
        String option = this.getInputOutputManager().askInformation("Inserire l'operazione desiderata:\n" +
                "\t- Incrementa di 1\n\t- Decrementa di 1");
        while (!(option.equalsIgnoreCase("incrementa") || option.equalsIgnoreCase("decrementa")))
            option = this.getInputOutputManager().askInformation("Errore: scelta non supportata, inserire (Decrementa/Incrementa): ");
        if (option.equalsIgnoreCase("incrementa"))
            infoUnit.setExtraParam(0);
        else
            infoUnit.setExtraParam(1);
        infoUnit.setDestinationIndex(this.getView().getGamePlayManager().choseCellWp(this.getView().getPlayer().getWp()));

        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL1));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 1");
        }
        */
    }

    @Override
    public void tool2(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();
        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL2));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 2");
        }
        */
    }

    @Override
    public void tool3(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();
        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL3));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 3");
        }
        */
    }

    @Override
    public void tool4(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();
        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL4));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 4");
        }
        */
    }

    @Override
    public void tool5(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();
        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL5));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 5");
        }
        */
    }

    @Override
    public void tool6(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();
        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL6));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 6");
        }
        */
    }

    @Override
    public void tool7(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();
        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL7));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 7");
        }
        */
    }

    @Override
    public void tool8(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();
        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL8));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 8");
        }
        */
    }

    @Override
    public void tool9(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();
        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL9));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 9");
        }
        */
    }

    @Override
    public void tool10(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();
        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL10));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 10");
        }
        */
    }

    @Override
    public void tool11(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();
        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL11));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 11");
        }
        */
    }

    @Override
    public void tool12() {
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();
        /*
        try{
            this.getServer().performToolCardMove(infoUnit, this.getSlotId(Commands.TOOL12));
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 12");
        }
        */
    }

    public int getSlotId(Commands command){
        int idSlot = -1;
        List<ToolCardView> cards = this.getView().getCommonBoard().getToolCardViews();

        for (ToolCardView tool : cards)
            if (tool.getCommand().equals(command))
                return cards.indexOf(tool);
        return idSlot;
    }
}
