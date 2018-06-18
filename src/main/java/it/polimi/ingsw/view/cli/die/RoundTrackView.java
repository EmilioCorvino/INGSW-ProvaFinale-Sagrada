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

    private InputOutputManager inputOutputManager;


    public RoundTrackView(int nRounds, InputOutputManager inputOutputManager){
        this.inputOutputManager = inputOutputManager;
        this.availableDice = new ArrayList<>();
        for( int i = 0; i< nRounds; i++)
            this.getAvailableDice().add(new ArrayList<>());
    }

    public void printRoundTrack(){
        inputOutputManager.print("\nTRACCIATO DEI ROUND: ");
        inputOutputManager.print(roundTrackToString());
    }

    private String roundTrackToString(){
        StringBuilder roundTrack = new StringBuilder(100);

        for(int i = 0; i < availableDice.size(); i++){
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
