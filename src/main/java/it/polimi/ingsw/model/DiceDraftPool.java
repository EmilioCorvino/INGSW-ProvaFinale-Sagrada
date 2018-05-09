package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class menage the DiceDraftPool
 */
public class DiceDraftPool extends ADieContainer{

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
     * This method call the update the DiceDraftPool in each round.
     */
    public void populateDiceDraftPool(int numberOfPlayers){

        int i = 0;
        boolean empty = false;
        Die dieExtracted;

        while( !empty && i < 2*numberOfPlayers + 1){
            dieExtracted = diceBag.extract();
            if (dieExtracted == null)
                empty = true;
            else
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
     * This method update the DraftPool list's of dice, removing the die chosen.
     * @param die: A copy of the die that has to be removed.
     */
    @Override
    public void update(Die die) {
        Die dieToBeDeleted = null;
        for( Die d : this.getAvailableDice())
            if(d.getDieColor() == die.getDieColor() && d.getActualDieValue() == die.getActualDieValue())
                dieToBeDeleted = d;
        this.getAvailableDice().remove(dieToBeDeleted);
    }
}
