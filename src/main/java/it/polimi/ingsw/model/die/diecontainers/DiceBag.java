package it.polimi.ingsw.model.die.diecontainers;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.utils.exceptions.DieNotContainedException;
import it.polimi.ingsw.utils.exceptions.EmptyException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

/**
 * This class menage the bag with the dice.
 */
public class DiceBag extends ADieContainer {

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
                availableDice.add(new Die(1 + new Random().nextInt(6), Color.availableColors().get(i)));
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
    public Die extract() throws EmptyException{
        if(!availableDice.isEmpty()) {
            int indexToBeExtracted = new Random().nextInt(getAvailableDice().size());
            return removeDie(indexToBeExtracted);
        }
        else
            throw new EmptyException("The dice bag is empty");
    }

    /**
     * This method addDie the list of available dice.
     * @param index a copy of the die to be removed.
     * @return the die contained, that has been removed.
     */
    @Override
    public Die removeDie(int index){
        return this.availableDice.remove(index);
    }

    /**
     * This method addDie a die in the bag
     * @param die: the die that has to be placed.
     */
    @Override
    public void addDie(Die die) {
        availableDice.add(die);
    }

    @Override
    public boolean isContained(Die die) {
        return false;
    }




}
