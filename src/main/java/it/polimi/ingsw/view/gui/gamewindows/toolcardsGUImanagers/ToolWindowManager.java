package it.polimi.ingsw.view.gui.gamewindows.toolcardsGUImanagers;


import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.view.gui.GUICommunicationManager;
import it.polimi.ingsw.view.gui.gamewindows.CommonBoardWindow;

import java.util.HashMap;
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

        toolMoveValidator.put(1, this::validateToolOne);

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