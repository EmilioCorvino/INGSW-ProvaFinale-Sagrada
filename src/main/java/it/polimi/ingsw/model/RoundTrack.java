package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class RoundTrack extends ADieContainer{

    /**
     * The list contains a list for each round, with the dice been left in the draft pool at the end of the round.
     */
    private List<ArrayList<Die>> availableDice;

    /**
     * The index of round where is the die that needs to be update
     */
    private int round;

    public RoundTrack(){
        this.availableDice = new ArrayList<>();
    }

    @Override
    public void update(Die die) {
        this.getAvailableDice().get(round).add(die);
    }

    public Die remove(Die die){
        Die dieToBeRemoved = null;
        for (Die d : this.getAvailableDice().get(round))
            if(die.getDieColor() == d.getDieColor() && die.getActualDieValue() == d.getActualDieValue())
                dieToBeRemoved = d;
        this.getAvailableDice().get(round).remove(dieToBeRemoved);
        return dieToBeRemoved;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public List<ArrayList<Die>> getAvailableDice() {
        return availableDice;
    }
}
