package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.Color;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WpGui extends Pane {

    private static final int MAX_ROW = 4;
    private static final int MAX_COL = 5;


    private GridPane glassWindow;

    private Label idMap;

    private Label difficulty;

    private Map<Color, String> colorMap;

    public WpGui() {

        idMap = new Label();
        difficulty = new Label();

        glassWindow = new GridPane();
        initializeGlassWindow();

        colorMap = new HashMap<>();
        initializeColorMap();

        //this.setMinHeight(100);
       // this.setMinWidth(100);

        //this.glassWindow.setPadding(new Insets(10));
    }


    public void initializeGlassWindow() {

        this.glassWindow.getStylesheets().add("style/backgrounds.css");
        //this.glassWindow.getStyleClass().add("map-background");

        //this.glassWindow.setMinHeight(160);
        //this.glassWindow.setMinWidth(180);

        for(int i=0; i<WpGui.MAX_ROW; i++)
            for(int j=0; j<WpGui.MAX_COL; j++) {
                Pane pane = new Pane();
                glassWindow.add(pane, j, i);
            }
    }


    public void initializeColorMap() {
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
                RowConstraints rc = new RowConstraints(40);
                this.glassWindow.getRowConstraints().add(rc);
                for(int j=0; j<WpGui.MAX_COL; j++, k++) {
                    ColumnConstraints cc = new ColumnConstraints(40);
                    this.glassWindow.getColumnConstraints().add(cc);
                    SetUpInformationUnit info = list.get(k);
                    Pane pane = new Pane();
                    //restriction of color
                    if(!(info.getColor().equals(Color.BLANK)) && info.getValue() == 0) {
                        pane.setStyle("-fx-background-color:" + this.colorMap.get(info.getColor()));
                        this.glassWindow.add(pane, j, i);
                    }
                        //restriction of value
                        if(info.getColor().equals(Color.BLANK) && info.getValue() !=0) {
                            TextField number = new TextField(info.getValue() + "");
                            //number.getStyleClass().add("text-label");
                            pane.getChildren().add(number);
                            number.getStyleClass().add("-fx-background-color: rgba(0, 0, 51, 0.3)");
                            this.glassWindow.add(pane, j, i);
                        }

                    if(info.getColor().equals(Color.BLANK)  && info.getValue() == 0) {
                        pane.setStyle("-fx-background-color: rgba(0, 0, 51, 0.3)");
                        this.glassWindow.add(pane, j, i);

                    }


                }
            }







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
