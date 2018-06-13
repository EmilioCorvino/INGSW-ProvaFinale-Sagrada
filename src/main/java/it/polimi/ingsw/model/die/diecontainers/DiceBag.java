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
            Die dieToBeExtracted = availableDice.get((new Random()).nextInt(getAvailableDice().size()));
            try {
                removeDie(dieToBeExtracted);
            } catch (DieNotContainedException e) {
                SagradaLogger.log(Level.SEVERE, "Die not contained in the dice bag");
            }
            return dieToBeExtracted;
        }
        else
            throw new EmptyException("The dice bag is empty");
    }

    /**
     * This method addDie the list of available dice.
     * @param die: the die that has to be placed. (must be a die extracted from the list) (same Object)
     */
    @Override
    public void removeDie(Die die) throws DieNotContainedException{
        for(Die d : availableDice){
            if(d.getDieColor().equals(die.getDieColor()) && d.getActualDieValue() == die.getActualDieValue()) {
                availableDice.remove(d);
                return;
            }
        }
        throw new DieNotContainedException("Want to remove a die not contained");
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
