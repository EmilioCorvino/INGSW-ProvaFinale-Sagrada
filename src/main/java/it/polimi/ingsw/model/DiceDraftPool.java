package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class menage the DiceDraftPool
 */
public class DiceDraftPool extends ADieContainer{

    /**
     * Number of player that are playing the match.
     */
    private int numberOfPlayers;

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
    public DiceDraftPool(int numberOfPlayers){

        this.numberOfPlayers = numberOfPlayers;
        this.availableDice = new ArrayList<>();
        this.diceBag = new DiceBag();
    }

    public List<Die> getAvailableDice(){
        return this.availableDice;
    }

    /**
     * This method call the update the DiceDraftPool in each round.
     */
    public void pooulateDiceDraftPool(){

        int i = 0;
        while( diceBag.extract() != null && i < 2*numberOfPlayers + 1){
            update(diceBag.extract());
            i++;
        }
    }

    //public Die chooseDie(Die die){

    //}

    @Override
    public void update(Die die) {
        availableDice.add(die);
    }
}
