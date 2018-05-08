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
    private static final int dieNumberForEachColor = 18;

    /**
     * Number of colors available.
     */
    private static final int numberOfColor = 5;

    /**
     * The constructor create the list of die and populates it with some dies for each color, with a random value.
     */
    public DiceBag(){
        this.availableDice = new ArrayList<>();
        for(int i = 0; i < numberOfColor; i++)
            for (int j = 0; j< dieNumberForEachColor; j++)
                availableDice.add(new Die(1 + new Random().nextInt(6) , Color.values()[i]));
    }


    /**
     * This method extract a Die from the List of available Dice, and remove it from there.
     * @return The die removed from the bag, with a random color.
     */
    public Die extract(){
        if(availableDice.size() != 0) {
            Die dieToBeExtracted = availableDice.get((new Random()).nextInt(90));
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
