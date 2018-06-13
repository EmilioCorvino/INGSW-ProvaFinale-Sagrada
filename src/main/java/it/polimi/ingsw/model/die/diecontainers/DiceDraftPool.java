package it.polimi.ingsw.model.die.diecontainers;

import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.utils.exceptions.DieNotContainedException;
import it.polimi.ingsw.utils.exceptions.EmptyException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * This class menage the DiceDraftPool
 */
public class DiceDraftPool extends ADieContainer {

    /**
     * List of Dice in the bag.
     */
    private List<Die> availableDice;

    /**
     * The die container where the DiceDraftPool extract dice.
     */
    private DiceBag diceBag;

    /**
     * The constructor
     */
    public DiceDraftPool(){
        this.availableDice = new ArrayList<>();
        this.diceBag = new DiceBag();
    }

    public List<Die> getAvailableDice(){
        return this.availableDice;
    }

    /**
     * This method call the addDie the DiceDraftPool in each round.
     */
    public void populateDiceDraftPool(int numberOfPlayers){
        int i = 0;
        Die dieExtracted = null;

        while(i < 2*numberOfPlayers + 1){
            try {
                dieExtracted = diceBag.extract();
            } catch (EmptyException e){
                SagradaLogger.log(Level.SEVERE, e.getMessage(), e);
            }
            this.getAvailableDice().add(dieExtracted);
            i++;
        }
    }

    /**
     * This method returns the die's chosen.
     * @param die: A copy of die chosen.
     * @return The reference at the die chosen
     */
    public Die chooseDie(Die die){
        for( Die d : this.getAvailableDice())
            if(d.getDieColor() == die.getDieColor() && d.getActualDieValue() == die.getActualDieValue())
                return d;
        return null;
    }

    /**
     * This method addDie the DraftPool list's of dice, removing the die chosen.
     * @param die: A copy of the die that has to be removed.
     */
    @Override
    public void removeDie(Die die) throws DieNotContainedException {
        for( Die d : this.getAvailableDice()) {
            if (d.getDieColor() == die.getDieColor() && d.getActualDieValue() == die.getActualDieValue()) {
                this.getAvailableDice().remove(d);
                return;
            }
        }
        throw new DieNotContainedException("Want to remove a die not contained");
    }

    /**
     * This method addDie a die in the draft.
     * @param die: the die that has to be placed.
     */
    @Override
    public void addDie(Die die) {
        this.getAvailableDice().add(die);
    }

    @Override
    public boolean isContained(Die die) {
        return false;
    }



}
