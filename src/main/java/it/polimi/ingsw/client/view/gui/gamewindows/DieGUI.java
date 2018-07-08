package it.polimi.ingsw.client.view.gui.gamewindows;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


/**
 * This class represents the die in the GUI view of the game.
 */
public class DieGUI extends StackPane {

    /**
     * The color of the die.
     */
    private Pane color;

    /**
     * The container for the image value of the die.
     */
    private ImageView value;

    /**
     * The effective numeric value of the die.
     */
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

        this.setMinHeight(45);
        this.setMinWidth(45);
        this.setMaxHeight(45);
        this.setMaxWidth(45);

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
