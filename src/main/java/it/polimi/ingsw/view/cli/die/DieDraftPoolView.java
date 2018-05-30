package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;
import it.polimi.ingsw.view.cli.InputOutputManager;
import java.util.List;

public class DieDraftPoolView {

    /**
     * This object manage the input output communication with the user.
     */
    private InputOutputManager inputOutputManager;

    /**
     * The list of dice contained.
     */
    private List<DieView> dice;

    public DieDraftPoolView(DiceDraftPool draft){
        this.inputOutputManager = new InputOutputManager();

        for(Die die: draft.getAvailableDice())
            dice.add(new DieView(die.getDieColor(),die.getActualDieValue()));
    }

    /**
     * This method print the draft pool
     */
    public void printDraftPool(){
        inputOutputManager.print("RISERVA:");
        inputOutputManager.print(diceDraftToString());
    }

    /**
     * This method create a draft pool in a string format with: DIE (number colored)
     * @return The draft pool in string format.
     */
    private String diceDraftToString(){
        String diceDraft = "|";

        for(int i = 0; i < dice.size(); i++)
            diceDraft += " " + dice.get(i).toStringDie() + " |";

        return diceDraft;
    }

    public List<DieView> getDice() {
        return dice;
    }
}