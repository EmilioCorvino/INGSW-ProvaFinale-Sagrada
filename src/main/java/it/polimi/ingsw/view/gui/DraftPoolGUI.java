package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

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

    private int numOfDice;

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
            this.getChildren().remove(0, diceList.size());

        this.numOfDice = diceList.size();
        diceList.forEach( info -> {
            this.isEmpty = false;
            DieGUI die = dieFactory.getsDieGUI(info);
            this.add(die, info.getDestinationIndex() % MAX_COL, info.getDestinationIndex() / MAX_COL);
        });
    }

    public void cellAsSource(PlayersData data) {
        for(int i=0; i< numOfDice; i++) {
            this.getChildren().get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                SetUpInformationUnit info = data.getSetUpInformationUnit();
                info.setSourceIndex(GridPane.getRowIndex((Node)e.getSource()) * DraftPoolGUI.MAX_COL + GridPane.getColumnIndex((Node)e.getSource()));
            });
        }
    }


}