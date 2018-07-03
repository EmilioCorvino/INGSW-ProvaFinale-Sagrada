package it.polimi.ingsw.view.gui.gamewindows.toolcardsGUImanagers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ToolCardGUI extends VBox {

    private int idTool;

    private int toolSlot;


    public ToolCardGUI(String path) {
        this.getStylesheets().add("style/backgrounds.css");

        Image tool = new Image(path);
        ImageView view = new ImageView(tool);
        view.setFitHeight(240);
        view.setPreserveRatio(true);

        this.getChildren().add(view);

        HBox toolInfo = new HBox();
        this.getChildren().add(toolInfo);

        HBox costInfo = new HBox();
        Label titleCost = new Label("Costo:");
        costInfo.getChildren().add(titleCost);
        Label cost = new Label("");
        costInfo.getChildren().add(cost);

        toolInfo.getChildren().add(costInfo);

        Button okButton = new Button("Ok");
        okButton.setVisible(false);
        okButton.getStyleClass().add("button-style");
        toolInfo.getChildren().add(okButton);

    }

    public int getIdTool() {
        return idTool;
    }

    public void setIdTool(int idTool) {
        this.idTool = idTool;
    }

    public int getToolSlot() {
        return toolSlot;
    }

    public void setToolSlot(int toolSlot) {
        this.toolSlot = toolSlot;
    }

}