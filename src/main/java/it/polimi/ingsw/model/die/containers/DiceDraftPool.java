package it.polimi.ingsw.model.die.containers;

import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.utils.SagradaLogger;
import it.polimi.ingsw.utils.exceptions.EmptyException;

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
    private List<Die> availableDiceCopy;

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
     * This method call the addDieToCopy the DiceDraftPool in each round.
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
     * This method addDieToCopy a die in the draft.
     * @param die: the die that has to be placed.
     */
    @Override
    public void addDieToCopy(Die die) {
        this.availableDiceCopy.add(die);
    }

    /**
     * This method addDieToCopy the DraftPool list's of dice, removing the die chosen.
     * @param index a copy of the die to be removed.
     * @return the die contained, that has been removed.
     */
    @Override
    public Die removeDieFromCopy(int index){
        return this.availableDiceCopy.remove(index);
    }

    /**
     * This method creates a copy of the objects contained.
     */
    @Override
    public void createCopy(){
        this.availableDiceCopy = new ArrayList<>();
        draftCopy(this.availableDice, this.availableDiceCopy);
    }

    /**
     * This method moves the copy of the objects contained to the original.
     */
    @Override
    public void overwriteOriginal(){
        draftCopy(this.availableDiceCopy, this.availableDice);
    }

    /**
     * This method copy the source in the destination.
     * @param source: the original component.
     * @param destination: the final component
     */
    private void draftCopy(List<Die> source, List<Die> destination){
        destination.clear();
        for (Die d : source)
            destination.add(new Die(d.getActualDieValue(),d.getDieColor()));
    }

    public List<Die> getAvailableDiceCopy(){
        return this.availableDiceCopy;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }
}
