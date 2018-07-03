package it.polimi.ingsw.view.gui.gamewindows;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.view.gui.gamewindows.toolcardsGUImanagers.ToolWindowManager;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the draft pool of the game in the GUI view.
 */
public class DraftPoolGUI extends GridPane {

    /**
     * The maximum number of rows the draft-grid has.
     */
    private static final int MAX_ROW = 3;

    /**
     * The maximum number of columns the draft-grid has.
     */
    private static final int MAX_COL = 3;

    /**
     * This attribute represents the object to construct a single die.
     */
    private DieFactory dieFactory;

    /**
     * This attribute indicates if the draft pool is already empty or not.
     */
    private boolean isEmpty = true;

    /**
     * This attribute manages the handlers related to the chosen tool.
     */
    private ToolWindowManager toolManager;

    /**
     * This attribute represents the value of the chosen die of the draft.
     */
    private int currValue;

    private int indexChosenCell;

    private boolean draftCellChosen;

    public DraftPoolGUI() {
        this.getStylesheets().add("style/backgrounds.css");
        this.getStyleClass().add("map-background");
        this.setPadding(new Insets(20));
        this.setMinWidth(200);
        this.setMaxWidth(200);
        this.setHgap(12.5);
        this.setVgap(12.5);

        dieFactory = new DieFactory();

        for(int i=0; i< MAX_ROW; i++) {
            RowConstraints rc = new RowConstraints(45);
            this.getRowConstraints().add(rc);
            for(int j=0; j<MAX_COL; j++) {
                ColumnConstraints cc = new ColumnConstraints(45);
                this.getColumnConstraints().add(cc);

            }
        }

    }

    /**
     * This method populates the draft-grid of the game in the GUI version.
     * @param diceList the list of dice to populate the draft pool.
     */
    public void formatDraftPool(List<SetUpInformationUnit> diceList) {
        if(!this.isEmpty)
            this.getChildren().remove(0, this.getChildren().size());

        diceList.forEach( info -> {
            this.isEmpty = false;
            DieGUI die = dieFactory.getsDieGUI(info);
            this.add(die, info.getDestinationIndex() % MAX_COL, info.getDestinationIndex() / MAX_COL);
        });

    }

    /**
     * This method sets the current value of the chosen die.
     * @param i the index of the current value chosen by the player.
     */
    public void setCurr(int i) {
        int val = ((DieGUI)this.getChildren().get(i)).getDieValue();
        setCurrValue(val);
    }

    /**
     * This method adds the handler to the dice available in the draft pool.
     * //@param data the data where to store the input of the user.
     */
    public void cellAsSource() {
        for(int i=0; i<this.getChildren().size(); i++)
                this.getChildren().get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    setIndexChosenCell(GridPane.getRowIndex((Node)e.getSource()) * DraftPoolGUI.MAX_COL + GridPane.getColumnIndex((Node)e.getSource()));
                   this.draftCellChosen = true;
                    int index = GridPane.getRowIndex((Node)e.getSource()) * DraftPoolGUI.MAX_COL + GridPane.getColumnIndex((Node)e.getSource());
                    setCurr(index);
                });
    }


    /**
     * This method flows the dice of the draft after a dice has benn removed for a placement.
     */
    public void reFormatDraft() {

        List dice = new ArrayList();
        for(int i=0; i<this.getChildren().size(); i++)
            dice.add(this.getChildren().get(i));

        this.getChildren().remove(0, this.getChildren().size());

        for(int i=0; i< dice.size(); i++)
            this.add((DieGUI)dice.get(i), i%3, i/3);
    }

    public ToolWindowManager getToolManager() {
        return toolManager;
    }

    public void setToolManager(ToolWindowManager toolManager) {
        this.toolManager = toolManager;
    }

    public int getCurrValue() {
        return currValue;
    }

    public void setCurrValue(int currValue) {
        this.currValue = currValue;
    }

    public int getIndexChosenCell() {
        return indexChosenCell;
    }

    public void setIndexChosenCell(int indexChosenCell) {
        this.indexChosenCell = indexChosenCell;
    }

    public boolean isDraftCellChosen() {
        return draftCellChosen;
    }

    public void setDraftCellChosen(boolean draftCellChosen) {
        this.draftCellChosen = draftCellChosen;
    }
}