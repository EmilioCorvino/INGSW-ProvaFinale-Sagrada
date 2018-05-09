package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class menage the bag with the dice.
 */
public class DiceBag extends ADieContainer{

    /**
     * List of Dice in the bag.
     */
    private List<Die> availableDice;

    /**
     * Number of dice for color.
     */
    private static final int DICE_NUMBER_FOR_EACH_COLOR = 18;

    /**
     * Number of colors available.
     */
    private static final int NUMBER_OF_COLORS = 5;

    /**
     * The constructor create the list of die and populates it with some dies for each color, with a random value.
     */
    public DiceBag(){
        this.availableDice = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_COLORS; i++)
            for (int j = 0; j< DICE_NUMBER_FOR_EACH_COLOR; j++)
                availableDice.add(new Die(1 + new Random().nextInt(6), Color.values()[i]));
    }

    public List<Die> getAvailableDice() {
        return availableDice;
    }

    public static int getDieNumberForEachColor() {
        return DICE_NUMBER_FOR_EACH_COLOR;
    }

    public static int getNumberOfColors() {
        return NUMBER_OF_COLORS;
    }

    /**
     * This method extract a Die from the List of available Dice, and remove it from there.
     * @return The die random removed from the bag.
     */
    public Die extract(){
        if(!availableDice.isEmpty()) {
            Die dieToBeExtracted = availableDice.get((new Random()).nextInt(getAvailableDice().size()));
            update(dieToBeExtracted);
            return dieToBeExtracted;
        }else
            return null;
    }

    /**
     * This method update the list of available dice.
     * @param die: the die that has to be placed. (must be a die extracted from the list) (same Object)
     */
    @Override
    public void update(Die die) {
        availableDice.remove(die);
    }

}
