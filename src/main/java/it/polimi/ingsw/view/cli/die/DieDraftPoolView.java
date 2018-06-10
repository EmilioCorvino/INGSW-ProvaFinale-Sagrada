package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.view.cli.InputOutputManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This class identify the list of die that can be place in each turn.
 */
public class DieDraftPoolView {

    /**
     * This object manage the input output communication with the user.
     */
    private InputOutputManager inputOutputManager;

    /**
     * The list of dice contained.
     */
    private List<DieView> dice;


    public DieDraftPoolView(List<SetUpInformationUnit> draft){
        this.inputOutputManager = new InputOutputManager();
        this.dice = new ArrayList<>();

        for(SetUpInformationUnit die: draft)
            dice.add(new DieView(die.getColor(),die.getValue()));
    }

    /**
     * This method print the draft pool
     */
    public void printDraftPool(){
        inputOutputManager.print("\nRISERVA:");
        inputOutputManager.print(diceDraftToString());
    }

    /**
     * This method create a draft pool in a string format with: DIE (number colored)
     * @return The draft pool in string format.
     */
    private String diceDraftToString(){
        StringBuilder diceDraft = new StringBuilder(100);
        diceDraft.append("|");

        for(DieView d : this.dice)
            diceDraft.append(" ").append(d.toStringDie()).append(" |");

        return diceDraft.toString();
    }

    public List<DieView> getDice() {
        return dice;
    }
}
