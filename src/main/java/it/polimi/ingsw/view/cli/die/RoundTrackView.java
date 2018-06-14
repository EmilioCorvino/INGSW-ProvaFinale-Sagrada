package it.polimi.ingsw.view.cli.die;

import java.util.ArrayList;
import java.util.List;

/**
 * This class identify the round track inside the view.
 */
public class RoundTrackView {

    /**
     * The list contains a list for each round, with the dice been left in the draft pool at the end of the round.
     */
    private List<ArrayList<DieView>> availableDice;


    public RoundTrackView(int nRounds){
        this.availableDice = new ArrayList<>();
        for( int i = 0; i< nRounds; i++)
            this.getAvailableDice().add(new ArrayList<>());
    }

    public void printRoundTrack(){

    }

    public List<ArrayList<DieView>> getAvailableDice() {
        return availableDice;
    }

}
