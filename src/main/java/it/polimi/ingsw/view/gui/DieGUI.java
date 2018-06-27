package it.polimi.ingsw.view.gui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class DieGUI extends StackPane {

    private Pane color;

    private ImageView value;


    public DieGUI() {
        color = new Pane();
        value = new ImageView();

        value.setFitHeight(45);
        value.setPreserveRatio(true);
        this.getChildren().add(color);
        this.getChildren().add(value);
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
}
