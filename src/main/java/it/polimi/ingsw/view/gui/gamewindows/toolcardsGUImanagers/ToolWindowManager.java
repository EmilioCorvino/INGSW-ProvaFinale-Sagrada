package it.polimi.ingsw.view.gui.gamewindows.toolcardsGUImanagers;


import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.view.gui.GUICommunicationManager;
import it.polimi.ingsw.view.gui.gamewindows.CommonBoardWindow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class manages the tool cards that are chosen by the user during the game, in the GUI view.
 */
public class ToolWindowManager {

    /**
     * This attribute represents the manager to let the GUI communicate with the user and the server.
     */
    private GUICommunicationManager manager;

    /**
     * This is the GUI common board of the entire game.
     */
    private CommonBoardWindow commonBoardWindow;

    /**
     * This attribute maps a specific tool to the method used to support the execution of the effect of the tool itsel,
     * such as some support window to manipulate the chosen die, for example in the tool card number one.
     */
    private Map<Integer, Runnable> toolMethods;

    /**
     * This attribute maps each tool card to its specific method used to validate the effect of the tool, preventing
     * some missing important fields to send to the server.
     */
    private Map<Integer, Runnable> toolMoveValidator;

    /**
     * The id of the slot of the tool card chosen by the user.
     */
    private int slotId;

    /**
     * The id of the tool chosen by the player.
     */
    private int currTool;

    /**
     * This attribute represents a builder for support windows of the tool cards, for example the support
     * window for the tool card number one.
     */
    private ToolWindowBuilder toolWindowBuilder;


    public ToolWindowManager(CommonBoardWindow commonBoardWindow) {
        this.commonBoardWindow = commonBoardWindow;
        toolMethods = new HashMap<>();
        toolMoveValidator = new HashMap<>();

        toolMethods.put(1, this::toolOneWindow);
        toolMethods.put(2, this::toolTwoThreeWindow);
        toolMethods.put(3, this::toolTwoThreeWindow);
        toolMethods.put(4, this::toolFourWindow);
        toolMethods.put(5, this::toolFiveWindow);

        toolMoveValidator.put(1, this::validateToolOne);
        toolMoveValidator.put(2, this::toolTwoValidator);
        toolMoveValidator.put(3, this::toolThreeValidator);
        toolMoveValidator.put(4, this::toolFourValidator);
        toolMoveValidator.put(5, this::toolFiveValidator);

        toolWindowBuilder = new ToolWindowBuilder(commonBoardWindow);
    }

    /**
     * This method is used to perform important actions on the tool card on the GUI chosen by the player.
     * @param toolId the id of the tool chosen by the player.
     */
    public void invokeToolCommand(int toolId) {
        this.currTool = toolId;
        this.toolMethods.get(toolId).run();
    }

    /**
     * This method is used to invoke the method that checks if the inputs from the user are complete and
     * correct to send to the server.
     * @param toolId the id of the tool chosen by the player.
     */
    public void invokeMoveValidator(int toolId) {
        this.toolMoveValidator.get(toolId).run();
    }


    /**
     * This method validates the inputs of the player when tool card number one is chosen.
     */
    private void validateToolOne() {
        SetUpInformationUnit info = this.commonBoardWindow.getData().getSetUpInformationUnit();
        info.setSourceIndex(this.commonBoardWindow.getDraftPoolGUI().getIndexChosenCell());
        info.setDestinationIndex(this.commonBoardWindow.getData().getPersonalWp().getClicked());
        this.manager.executeCommandToolIfPresent(this.currTool);

    }

    /**
     * This method shows the support window to perform the effect of the tool card number one.
     */
    private void toolOneWindow() {
            if(this.commonBoardWindow.getDraftPoolGUI().isDraftCellChosen())
                this.toolWindowBuilder.showSupportWindowToolOne();
            else
                this.commonBoardWindow.sendMessage("Devi scegliere un dado dalla riserva.");
    }

    private void toolTwoThreeWindow() {
        List<Integer> values = this.commonBoardWindow.getData().getPersonalWp().getCellsClicked();

        if(values.size() > 0)
            values.clear();

        this.commonBoardWindow.sendMessage("Scegli una cella sorgente della tua mappa, poi scegli"
        + " una cella destinazione della tua mappa in cui mettere il dado che vuoi spostare.");
    }

    private void toolTwoValidator() {
        List<Integer> values = this.commonBoardWindow.getData().getPersonalWp().getCellsClicked();
        if(values.size() > 2) {
            this.manager.communicateMessage("Devi scegliere solo due celle della tua mappa personale;" +
                        "ignora le restrizioni di COLORE");
            values.clear();
            return;
        }

        if(values.size() < 2)
            this.commonBoardWindow.sendMessage("Devi scegliere due celle della tua mappa personale." +
                    " Ignora le restrizioni di COLORE.");
        else {
            this.manager.executeCommandToolIfPresent(2);
        }
    }

    public void toolThreeValidator() {
        List<Integer> values = this.commonBoardWindow.getData().getPersonalWp().getCellsClicked();
        if(values.size() > 2) {
            this.manager.communicateMessage("Devi scegliere solo due celle della tua mappa personale;" +
                    "ignora le restrizioni di VALORE");
            values.clear();
            return;
        }

        if(values.size() < 2)
            this.commonBoardWindow.sendMessage("Devi scegliere due celle della tua mappa personale." +
                    " Ignora le restrizioni di VALORE.");
        else {
            this.manager.executeCommandToolIfPresent(3);
        }
    }

    public void toolFourWindow() {
        List<Integer> values = this.commonBoardWindow.getData().getPersonalWp().getCellsClicked();

        if(values.size() > 0)
            values.clear();

        this.commonBoardWindow.sendMessage("Scegli una cella sorgente della tua mappa, poi scegli"
                + " una cella destinazione della tua mappa in cui mettere il primo dado che vuoi spostare." +
                    " Fai la stessa cosa con il secondo dado.");

    }

    public void toolFourValidator() {
        List<Integer> values = this.commonBoardWindow.getData().getPersonalWp().getCellsClicked();

        if(values.size() != 4) {
            this.manager.communicateMessage("Devi scegliere esattamente due dadi. Scegli delle nuove coordinate per i due dadi.");
            values.clear();
        }
        else
            this.manager.executeCommandToolIfPresent(4);
    }

    public void toolFiveWindow() {
        this.manager.communicateMessage("Scegli un dado dalla draft, un dado della round track e infine una cella della tua mappa in cui posizionare il dado");
    }

    public void toolFiveValidator() {
        if(!this.commonBoardWindow.getData().getPersonalWp().isWpCellClicked()) {
            this.manager.communicateMessage("Devi scegliere una cella della tua mappa.");
            return;
        }

        if(!this.commonBoardWindow.getDraftPoolGUI().isDraftCellChosen()) {
            this.manager.communicateMessage("Devi scegliere un dado della riserva.");
            return;
        }

        if(!this.commonBoardWindow.getRoundTrack().isRoundChosen()) {
            this.manager.communicateMessage("Devi scegliere un dado dal tracciato dei round.");
            return;
        }

        this.manager.executeCommandToolIfPresent(5);
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public GUICommunicationManager getManager() {
        return manager;
    }

    public void setManager(GUICommunicationManager manager) {
        this.manager = manager;
    }
}