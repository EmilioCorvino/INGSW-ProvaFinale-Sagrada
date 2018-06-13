package it.polimi.ingsw.model.die.diecontainers;

import it.polimi.ingsw.model.die.Die;

import java.util.ArrayList;
import java.util.List;

public class RoundTrack extends ADieContainer {

    /**
     * The list contains a list for each round, with the dice been left in the draft pool at the end of the round.
     */
    private List<ArrayList<Die>> availableDice;

    /**
     * The index of round where is the die that needs to be addDie
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
        this.getAvailableDice().get(roundToBeUpdated).add(die);
    }

    /**
     * This method remove a die in the selected round in the round track.
     * @param die the die to remove.
     */
    @Override
    public void removeDie(Die die) {
        Die dieToBeRemoved = null;
        for (Die d : this.getAvailableDice().get(roundToBeUpdated))
            if(die.getDieColor() == d.getDieColor() && die.getActualDieValue() == d.getActualDieValue())
                dieToBeRemoved = d;
        this.getAvailableDice().get(roundToBeUpdated).remove(dieToBeRemoved);
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
