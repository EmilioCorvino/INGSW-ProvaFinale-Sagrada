package it.polimi.ingsw.common;

import java.util.ArrayList;
import java.util.List;

/**
 * This enum lists all the possible colors that can be used in the game.
 */
public enum Color {
    PURPLE(35,"P"),
    BLUE(34,"B"),
    GREEN(36,"G"),
    RED(31,"R"),
    YELLOW(33,"Y"),
    BLANK;

    /**
     * It is the number needed to print a string in this color
     */
    private int colorNumber;

    /**
     * It is the id of the color, to recognize a color from an other.
     */
    private String id;

    public int getColorNumber() {
        return colorNumber;
    }

    public String getId(){
        return id;
    }

    Color() {}

    Color(int colorNumber, String id){
        this.colorNumber = colorNumber;
        this.id = id;
    }

    public static List<Color> availableColors() {
        List<Color> colors = new ArrayList<>();
        for(Color c: Color.values()) {
            if(!c.equals(BLANK)) {
                colors.add(c);
            }
        }
        return colors;
    }
}