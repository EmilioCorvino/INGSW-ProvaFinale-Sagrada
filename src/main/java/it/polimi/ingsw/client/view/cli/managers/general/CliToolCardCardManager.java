package it.polimi.ingsw.client.view.cli.managers.general;

import it.polimi.ingsw.client.view.IToolCardManager;
import it.polimi.ingsw.client.view.cli.CliView;
import it.polimi.ingsw.client.view.cli.boardelements.ToolCardView;
import it.polimi.ingsw.client.view.cli.managers.CliCommunicationManager;
import it.polimi.ingsw.common.Commands;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * This class is composed by methods that call
 * {@link it.polimi.ingsw.server.controller.ServerImplementation#performToolCardMove(int, List)} giving in input
 * the slot in which the card is located and a {@link SetUpInformationUnit} filled with the information needed to perform
 * the move.
 */
public class CliToolCardCardManager extends CliCommunicationManager implements IToolCardManager {

    public CliToolCardCardManager(CliView view){
        super(view);
    }

    /**
     * This method is used to perform the move related to the Tool Card 1. The increment or decrement are set as a flag
     * inside {@link SetUpInformationUnit#extraParam}. Other than that, the source index is the index of the die inside
     * the Dice Draft Pool, while the destination is the index of the die inside the Window Pattern Card.
     */
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

    /**
     * This method is used to perform the move related to the Tool Card 2. The {@link SetUpInformationUnit} is filled
     * only with the source and the destination on the Window Pattern Card of the die to move.
     */
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

    /**
     * This method is used to perform the move related to the Tool Card 3. The {@link SetUpInformationUnit} is filled
     * only with the source and the destination on the Window Pattern Card of the die to move.
     */
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

    /**
     * This method is used to perform the move related to the Tool Card 4. Two {@link SetUpInformationUnit} are sent,
     * one for each movement (only source and destination are set in each SetUpInformationUnit).
     */
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

    /**
     * This method is used to perform the move related to the Tool Card 5. {@link SetUpInformationUnit#extraParam}
     * represents the round from where a die has to be swapped with one from the Dice Draft Pool
     * ({@link SetUpInformationUnit#sourceIndex}). The index of the specific die within that round is set in
     * {@link SetUpInformationUnit#offset}. The destination on the Window Pattern Card is set in
     * {@link SetUpInformationUnit#destinationIndex}.
     */
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

    /**
     * This method is used to perform the move related to the Tool Card 6. The {@link SetUpInformationUnit} is only filled
     * with the source index of the die inside the Dice Draft Pool: that die has to be drafted again.
     */
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

    /**
     * This method is used to perform the extra move required to use Tool Card 6. After the die has been drafted, it
     * has to be placed: this method does that by setting {@link SetUpInformationUnit#destinationIndex} to the desired
     * destination.
     */
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

    /**
     * This method is used to perform the move related to the Tool Card 7. In this case the {@link SetUpInformationUnit}
     * is empty, because the controller doesn't need any specific information to roll again all dice inside the
     * Dice Draft Pool.
     */
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

    /**
     * This method is used to perform the move related to the Tool Card 8. This card allows to perform an extra default
     * placement, therefore only {@link SetUpInformationUnit#sourceIndex} from the Dice Draft Pool and
     * {@link SetUpInformationUnit#destinationIndex} to the Window Pattern Card are set.
     */
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

    /**
     * This method is used to perform the move related to the Tool Card 9. This card allows to perform a default
     * placement, without respecting the rule of adjacency, therefore only {@link SetUpInformationUnit#sourceIndex}
     * from the Dice Draft Pool and {@link SetUpInformationUnit#destinationIndex} to the Window Pattern Card are set.
     */
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

    /**
     * This method is used to perform the move related to the Tool Card 10. This card allows to perform default
     * placement, after calculating the opposite of the value of the die retrieved from the source,
     * therefore only {@link SetUpInformationUnit#sourceIndex} from the Dice Draft Pool and
     * {@link SetUpInformationUnit#destinationIndex} to the Window Pattern Card are set.
     */
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

    /**
     * This method is used to perform the move related to the Tool Card 11. Only {@link SetUpInformationUnit#sourceIndex}
     * is set because the first step just consists in moving a die from the Dice Draft Pool to the Dice Bag.
     */
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

    /**
     * This method is used to perform the extra move required to use the Tool Card 11. This second steps allows to
     * choose the value of the die obtained by the Dice Bag, and then to place it. Therefore it sets
     * {@link SetUpInformationUnit#destinationIndex} to the destination on the Window Pattern Card, and
     * {@link SetUpInformationUnit#value} to the value chosen.
     *
     */
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

    /**
     * This method is used to perform the move related to the Tool Card 12. This card allows to move up to two dice in the
     * Window Pattern Card with the same colo as a die on the Round Track. Therefore, up to two {@link SetUpInformationUnit}
     * are prepared: each one has {@link SetUpInformationUnit#extraParam} set to the number of the round selected and
     * {@link SetUpInformationUnit#offset} set to the index of the particular die chosen within the round.
     * {@link SetUpInformationUnit#sourceIndex} and {@link SetUpInformationUnit#destinationIndex} are set to the source
     * and the destination within the Window Pattern Card.
     */
    @Override
    public void tool12() {
        List<SetUpInformationUnit> units = new ArrayList<>();
        SetUpInformationUnit infoUnit1 = new SetUpInformationUnit();
        SetUpInformationUnit infoUnit2 = new SetUpInformationUnit();
        int nDice;

        do {
            nDice = super.inputOutputManager.askInt("Inserisci quanti dadi vuoi muovere(1-2): ");
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

    /**
     * Retrieves the slot id of the command (representing the Tool Card) in input.
     * @param command command representing the tool card whose slot is to identify.
     * @return id of the slot in which the tool with specified command is located.
     */
    private int getSlotId(Commands command){
        int idSlot = -1;
        List<ToolCardView> cards = super.view.getCommonBoard().getToolCardViews();

        for (ToolCardView tool : cards)
            if (tool.getCommand().equals(command))
                return cards.indexOf(tool);
        return idSlot;
    }

    /**
     * Prints the description of the Tool Card in the specified slot.
     * @param id id of the slot in which the Tool Card is located.
     */
    private void printToolDescription(int id){
        if (id > -1 && id < 3)
            super.inputOutputManager.print("Descrizione tool:\n\t- "+super.view.getCommonBoard().getToolCardViews().get(id).getDescription());
    }

    /**
     * Retrieves the source and the destination of the die to move.
     * @param infoUnit object in which the source and the destination are set. This will be sent to the server.
     */
    private void fromWpToWp(SetUpInformationUnit infoUnit){

        super.inputOutputManager.print("Da:");
        infoUnit.setSourceIndex(super.view.getGamePlayManager().choseCellWp());

        super.inputOutputManager.print("A:");
        infoUnit.setDestinationIndex(super.view.getGamePlayManager().choseCellWp());
    }
}
