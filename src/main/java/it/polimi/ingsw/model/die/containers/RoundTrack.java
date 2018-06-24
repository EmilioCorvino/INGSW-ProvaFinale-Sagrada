package it.polimi.ingsw.model.die.containers;

import it.polimi.ingsw.model.die.Die;
import java.util.ArrayList;
import java.util.List;

public class RoundTrack extends ADieContainer {

    /**
     * The list contains a list for each round, with the dice been left in the draft pool at the end of the round.
     */
    private List<ArrayList<Die>> availableDice;

    /**
     * The copy of dice contained.
     */
    private List<ArrayList<Die>> availableDiceCopy;

    /**
     * The index of round where is the die that needs to be updated.
     */
    private int roundToBeUpdated;

    public RoundTrack(int nRounds){
        this.availableDice = new ArrayList<>();
        for( int i = 0; i< nRounds; i++)
            this.getAvailableDice().add(new ArrayList<>());
    }

    /**
     * This method effective addDie a die in the round chosen.
     * @param die: the die that has to be placed.
     */
    @Override
    public void addDie(Die die) {
        this.availableDiceCopy.get(roundToBeUpdated).add(die);
    }

    /**
     * This method remove a die in the selected round in the round track.
     * @param index a copy of the die to be removed.
     * @return the die contained, that has been removed.
     */
    @Override
    public Die removeDie(int index){
        return this.availableDiceCopy.get(roundToBeUpdated).remove(index);
    }

    @Override
    public void createCopy() {
        this.availableDiceCopy = new ArrayList<>();
        roundTrackCopy(this.availableDice, this.availableDiceCopy);
    }

    @Override
    public void overwriteOriginal() {
        roundTrackCopy(this.availableDiceCopy, this.availableDice);
    }

    private void roundTrackCopy(List<ArrayList<Die>> source, List<ArrayList<Die>> destination){
        destination.clear();
        for( int i = 0; i< 10; i++)
            destination.add(new ArrayList<>());
        for (int i = 0; i < source.size(); i++)
            for (Die d : source.get(i))
                destination.get(i).add(new Die(d.getActualDieValue(),d.getDieColor()));
    }

    @Override
    public boolean isContained(Die die) {
        return false;
    }

    public void setRoundToBeUpdated(int round) {
        this.roundToBeUpdated = round;
    }

    public List<ArrayList<Die>> getAvailableDice() {
        return availableDice;
    }
}
