package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.view.gui.gamewindows.DieGUI;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class manages the GUI version of the window pattern card of the game.
 */
public class WpGui extends Pane {

    /**
     * The number of rows of the map.
     */
    public static final int MAX_ROW = 4;

    /**
     * The number of columns of the map.
     */
    public static final int MAX_COL = 5;

    /**
     * The glass window that will contain die and restrictions.
     */
    private GridPane glassWindow;

    /**
     * The id of the map.
     */
    private Label idMap;

    /**
     * The difficulty of the map.
     */
    private Label difficulty;

    /**
     * A map that maps color from the server to colors to be represented in the gui.
     */
    private final Map<Color, String> colorMap;

    /**
     * The index of the cell clicked by the user.
     */
    private int clicked;

    private boolean isHandlerActive;

    /**
     * This represents a list of cells clicked.
     */
    private List<Integer> cellsClicked;

    /**
     * This indicates if a cell has been clicked or not.
     */
    private boolean wpCellClicked;

    public WpGui() {

        cellsClicked = new ArrayList<>();
        idMap = new Label();
        difficulty = new Label();

        glassWindow = new GridPane();

        colorMap = new HashMap<>();
        colorMap.put(Color.RED, "#DC143C");
        colorMap.put(Color.YELLOW, "#FFFF00");
        colorMap.put(Color.GREEN, "#2E8B57");
        colorMap.put(Color.BLUE, "#1E90FF");
        colorMap.put(Color.PURPLE, "#800080");
    }

    /**
     * This method constructs a specific map for a player.
     * @param map the info to use to construct the map.
     */
    public void constructMap(SimplifiedWindowPatternCard map) {

        this.idMap.setText("Mappa: " + map.getIdMap());
        this.difficulty.setText("Difficoltà: " + map.getDifficulty());

        this.idMap.getStyleClass().add("text-label");
        this.difficulty.getStyleClass().add("text-label");

        List<SetUpInformationUnit> list = map.getInformationUnitList();

        int k = 0;
        for(int i=0; i<WpGui.MAX_ROW; i++) {
            RowConstraints rc = new RowConstraints(45);
            this.glassWindow.getRowConstraints().add(rc);
            rc.setVgrow(Priority.ALWAYS);
            for(int j=0; j<WpGui.MAX_COL; j++, k++) {
                ColumnConstraints cc = new ColumnConstraints(45);
                cc.setHgrow(Priority.ALWAYS);
                this.glassWindow.getColumnConstraints().add(cc);
                SetUpInformationUnit info = list.get(k);
                Pane pane = new Pane();

                pane.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.MIDNIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                pane.setOpacity(0.2);

                StackPane stack = new StackPane();


                //restriction of color
                if(!(info.getColor().equals(Color.BLANK)) && info.getValue() == 0) {
                    Pane extraPane = new Pane();
                    extraPane.setStyle("-fx-background-color: transparent");
                    stack.getChildren().addAll(pane, extraPane);
                    checkBorder(pane,i, j);
                    pane.setStyle("-fx-background-color:" + this.colorMap.get(info.getColor()));
                    pane.setOpacity(0.6);
                    this.glassWindow.add(stack, j, i);
                }
                //restriction of value
                if(info.getColor().equals(Color.BLANK) && info.getValue() !=0) {
                    Label number = new Label(info.getValue() + "");
                    number.getStyleClass().add("text-label");
                    checkBorder(pane, i, j);
                    stack.getChildren().addAll(pane, number);
                    this.glassWindow.add(stack, j, i);
                }

                if(info.getColor().equals(Color.BLANK)  && info.getValue() == 0) {
                    stack.getChildren().add(pane);
                    checkBorder(pane, i, j);
                    this.glassWindow.add(stack, j, i);
                }
            }
        }
    }

    /**
     * This method sets the proper clicked cells as destination in the information unit to send to the server.
     * //@param data the data player from which to get the info.
     */
    public void cellMapAsDestinationHandler() {

        for(int i=0; i<WpGui.MAX_ROW; i++)
            for(int j=0; j<WpGui.MAX_COL; j++) {
                StackPane stack = (StackPane)this.glassWindow.getChildren().get(i * WpGui.MAX_COL + j);
                stack.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    this.wpCellClicked = true;
                    setClicked(GridPane.getRowIndex((Node)e.getSource()) * WpGui.MAX_COL + GridPane.getColumnIndex((Node)e.getSource()));
                    this.cellsClicked.add(GridPane.getRowIndex((Node)e.getSource()) * WpGui.MAX_COL + GridPane.getColumnIndex((Node)e.getSource()));

                });
            }
        }

    /**
     * This method adds a die on the window pattern card gui.
     * @param die the die to add.
     * @param index the index of the cell where to add the die.
     */
    public void addOnThisWp(DieGUI die, int index) {
            StackPane stack = (StackPane)this.glassWindow.getChildren().get(index);
            stack.getChildren().add(die);
    }

    public void removeFromThisWp(int source) {
        System.out.println("Sto per rimuovere l'elemento...sono in wp " + source);
        StackPane stack = (StackPane)this.glassWindow.getChildren().get(source);
        int indexToRem = stack.getChildren().size() - 1;
        stack.getChildren().remove(indexToRem);
    }

    /**
     * This method adds proper border to each pane of the cell.
     * @param pane the pane to style with border.
     * @param row the row of the cell.
     * @param col the column of the cell.
     */
    public void checkBorder(Node pane, int row, int col) {
        pane.setStyle("-fx-border-width: 1px 1px 1px 1px");

        if(row ==0 )
            pane.setStyle("-fx-border-width: 2px 0px 0px 0px");

        if(row == 3)
            pane.setStyle("-fx-border-width: 0px 0px 2px 0px");

        if(col == 0)
            pane.setStyle("-fx-border-width: 0px 0px 0px 2px");

        if(col == 4)
            pane.setStyle("-fx-border-width: 0px 2px 0px 0px");

        pane.setStyle("-fx-border-color: rgba(102, 255, 224, 0.5)");
    }

    public GridPane getGlassWindow() {
        return glassWindow;
    }

    public void setGlassWindow(GridPane glassWindow) {
        this.glassWindow = glassWindow;
    }

    public Label getIdMap() {
        return idMap;
    }

    public void setIdMap(Label idMap) {
        this.idMap = idMap;
    }

    public Label getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Label difficulty) {
        this.difficulty = difficulty;
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }

    public List<Integer> getCellsClicked() {
        return cellsClicked;
    }

    public void setCellsClicked(List<Integer> cellsClicked) {
        this.cellsClicked = cellsClicked;
    }

    public boolean isWpCellClicked() {
        return wpCellClicked;
    }

    public void setWpCellClicked(boolean wpCellClicked) {
        this.wpCellClicked = wpCellClicked;
    }

    public boolean isHandlerActive() {
        return isHandlerActive;
    }

    public void setHandlerActive(boolean handlerActive) {
        isHandlerActive = handlerActive;
    }
}