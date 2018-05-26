package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.view.cli.InputOutputManager;
import java.util.List;

public class ViewDieDraftPool {

    private InputOutputManager inputOutputManager;

    private List<ViewDie> dice;

    public ViewDieDraftPool(List<ViewDie> dice){
        this.inputOutputManager = new InputOutputManager();
        this.dice = dice;
    }


    public void printDraftPool(){
        inputOutputManager.print("RISERVA:");
        inputOutputManager.print(diceDraftToString());
    }

    private String diceDraftToString(){
        String diceDraft = "|";

        for(int i = 0; i < dice.size(); i++)
            diceDraft += " " + dice.get(i).toStringDie() + " |";

        return diceDraft;
    }

    public List<ViewDie> getDice() {
        return dice;
    }
}
