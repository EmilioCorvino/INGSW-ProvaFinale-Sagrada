package it.polimi.ingsw.view.gui.gamewindows;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class DieGUI extends StackPane {

    private Pane color;

    private ImageView value;

    private int dieValue;


    public DieGUI() {
        this.getStylesheets().add("style/backgrounds.css");

        color = new Pane();
        value = new ImageView();

        color.setMinHeight(45);
        color.setMinWidth(45);
        color.setMaxHeight(45);
        color.setMaxWidth(45);

        value.setFitHeight(45);
        value.setPreserveRatio(true);
        this.getChildren().add(color);
        this.getChildren().add(value);
        this.getStyleClass().add("dieChosen");

    }

    public Pane getColor() {
        return color;
    }

    public void setColor(Pane color) {
        this.color = color;
    }

    public ImageView getValue() {
        return value;
    }

    public void setValue(ImageView value) {
        this.value = value;
    }

    public int getDieValue() {
        return dieValue;
    }

    public void setDieValue(int dieValue) {
        this.dieValue = dieValue;
    }
}
