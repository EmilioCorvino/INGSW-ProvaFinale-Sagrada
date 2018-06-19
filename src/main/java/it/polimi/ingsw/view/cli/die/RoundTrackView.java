package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.view.cli.generalManagers.InputOutputManager;

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

    public String roundTrackToString(){
        StringBuilder roundTrack = new StringBuilder("\nTRACCIATO DEI ROUND: \n");

        for(int i = 0; i < availableDice.size(); i++){
            if (i == 9)
                roundTrack.append("\t").append(i+1).append(":|");
            else
                roundTrack.append("\t").append(i+1).append(": |");

            for (DieView d : availableDice.get(i))
                roundTrack.append(d.toStringDie()).append("|");
            roundTrack.append("\n");
        }

        return roundTrack.toString();
    }

    public List<ArrayList<DieView>> getAvailableDice() {
        return availableDice;
    }

}
