package it.polimi.ingsw.view.cli.generalmanagers;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.view.cli.CliCommunicationManager;
import it.polimi.ingsw.view.IToolCardManager;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.cli.boardElements.CommonBoardView;
import it.polimi.ingsw.view.cli.boardElements.ToolCardView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.util.List;

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
        this.commonBoard = this.getView().getCommonBoard();
        this.wpPlayer = this.getView().getPlayer().getWp();
    }

    @Override
    public void tool1(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL1);
        this.printToolDescription(toolSlot);

        this.getInputOutputManager().print(this.commonBoard.getDraftPool().diceDraftToString());
        this.getInputOutputManager().print(this.wpPlayer.wpToString());

        infoUnit.setSourceIndex(this.getView().getGamePlayManager().choseDraftDie(commonBoard.getDraftPool()));

        String option = this.getInputOutputManager().askInformation("Inserire l'operazione desiderata:\n" +
                "\t- Incrementa di 1\n\t- Decrementa di 1");
        while (!(option.equalsIgnoreCase("incrementa") || option.equalsIgnoreCase("decrementa")))
            option = this.getInputOutputManager().askInformation("Errore: scelta non supportata, inserire (Decrementa/Incrementa): ");
        if (option.equalsIgnoreCase("incrementa"))
            infoUnit.setExtraParam(0);
        else
            infoUnit.setExtraParam(1);

        infoUnit.setDestinationIndex(this.getView().getGamePlayManager().choseCellWp());

        /*
        try{
            this.getServer().performToolCardMove(infoUnit, toolSlot);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during use of tool 1");
        }
        */
    }

    @Override
    public void tool2(){
        SetUpInformationUnit infoUnit = new SetUpInformationUnit();

        int toolSlot = this.getSlotId(Commands.TOOL2);
        this.printToolDescription(toolSlot);

        this.fromWptoWp(infoUnit);

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

        int toolSlot = this.getSlotId(Commands.TOOL3);
        this.printToolDescription(toolSlot);

        this.fromWptoWp(infoUnit);
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

    private int getSlotId(Commands command){
        int idSlot = -1;
        List<ToolCardView> cards = this.getView().getCommonBoard().getToolCardViews();

        for (ToolCardView tool : cards)
            if (tool.getCommand().equals(command))
                return cards.indexOf(tool);
        return idSlot;
    }

    private void printToolDescription(int id){
        if (id > 0 && id < 3)
            this.getInputOutputManager().print("Descrizione tool:\n\t"+this.commonBoard.getToolCardViews().get(id).getDescription());
    }

    private void fromWptoWp(SetUpInformationUnit infoUnit){
        this.getInputOutputManager().print(this.wpPlayer.wpToString());

        this.getInputOutputManager().print("Da:");
        infoUnit.setSourceIndex(this.getView().getGamePlayManager().choseCellWp());

        this.getInputOutputManager().print("A:");
        infoUnit.setDestinationIndex(this.getView().getGamePlayManager().choseCellWp());
    }
}
