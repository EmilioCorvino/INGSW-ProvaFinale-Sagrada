package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.Color;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WpGui extends Pane {

    public static final int MAX_ROW = 4;
    public static final int MAX_COL = 5;

    private GridPane glassWindow;

    private Label idMap;

    private Label difficulty;

    private final Map<Color, String> colorMap;

    public WpGui() {

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

                    //restriction of color
                    if(!(info.getColor().equals(Color.BLANK)) && info.getValue() == 0) {
                        checkBorder(pane,i, j);
                        pane.setStyle("-fx-background-color:" + this.colorMap.get(info.getColor()));
                        pane.setOpacity(0.6);
                        this.glassWindow.add(pane, j, i);

                    }
                        //restriction of value
                        if(info.getColor().equals(Color.BLANK) && info.getValue() !=0) {
                            StackPane stack = new StackPane();
                            Label number = new Label(info.getValue() + "");
                            number.getStyleClass().add("text-label");
                            checkBorder(pane, i, j);
                            stack.getChildren().addAll(pane, number);
                            this.glassWindow.add(stack, j, i);
                        }

                    if(info.getColor().equals(Color.BLANK)  && info.getValue() == 0) {
                        checkBorder(pane, i, j);

                        this.glassWindow.add(pane, j, i);

                    }
                }
            }
    }

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

}