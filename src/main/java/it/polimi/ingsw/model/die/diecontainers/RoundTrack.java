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
     * The index of round where is the die that needs to be update
     */
    private int roundToBeUpdate;

    public RoundTrack(int nRounds){
        this.availableDice = new ArrayList<>();
        for( int i = 0; i< nRounds; i++)
            this.getAvailableDice().add(new ArrayList<>());
    }

    /**
     * This method effective add a die in the round chosen.
     * @param die: the die that has to be placed.
     */
    @Override
    public void update(Die die) {
        this.getAvailableDice().get(roundToBeUpdate).add(die);
    }

    /**
     * This method remove a die from the round chosen and return the instance of the die removed.
     * @param die: the die that has to be removed.
     * @return: the die removed.
     */
    public Die remove(Die die){
        Die dieToBeRemoved = null;
        for (Die d : this.getAvailableDice().get(roundToBeUpdate))
            if(die.getDieColor() == d.getDieColor() && die.getActualDieValue() == d.getActualDieValue())
                dieToBeRemoved = d;
        this.getAvailableDice().get(roundToBeUpdate).remove(dieToBeRemoved);
        return dieToBeRemoved;
    }

    public void setRoundToBeUpdate(int round) {
        this.roundToBeUpdate = round;
    }

    public List<ArrayList<Die>> getAvailableDice() {
        return availableDice;
    }
}
