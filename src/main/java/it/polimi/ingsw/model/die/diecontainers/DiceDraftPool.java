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
     * A copy of the list of dice contained inside the draft.
     */
    private List<Die> draftCopy;

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
     * This method addDie the DraftPool list's of dice, removing the die chosen.
     * @param index a copy of the die to be removed.
     * @return the die contained, that has been removed.
     */
    @Override
    public Die removeDie(int index){
        return this.getAvailableDice().remove(index);
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

    public void createCopy(){
        this.draftCopy = new ArrayList<>(this.availableDice);
    }

    public void overwriteOriginal(){
        this.availableDice = new ArrayList<>(this.draftCopy);
    }

    public List<Die> getDraftCopy(){
        return this.draftCopy;
    }
}
