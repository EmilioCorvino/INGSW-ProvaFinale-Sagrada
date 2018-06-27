package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the construction of a single die in GUI view.
 */
public class DieFactory {

    /**
     * A map of colors to color the GUI-die.
     */
    private final Map<Color, String> colorMap;

    public DieFactory() {
        colorMap = new HashMap<>();
        colorMap.put(Color.RED, "#DC143C");
        colorMap.put(Color.YELLOW, "#FFFF00");
        colorMap.put(Color.GREEN, "#2E8B57");
        colorMap.put(Color.BLUE, "#1E90FF");
        colorMap.put(Color.PURPLE, "#800080");
    }

    /**
     * This method constructs a single die given the proper information.
     * @param info the information used to construct a die.
     * @return the constructed die.
     */
    public DieGUI getsDieGUI(SetUpInformationUnit info) {
        DieGUI die = new DieGUI();
        die.getChildren().get(0).setStyle("-fx-background-color:" + colorMap.get(info.getColor()));
        die.getChildren().get(0).setOpacity(0.6);

        Image valueImg = new Image("/diceValues/die" + info.getValue() + ".png");
        ((ImageView)die.getChildren().get(1)).setImage(valueImg);

       return die;
    }
}