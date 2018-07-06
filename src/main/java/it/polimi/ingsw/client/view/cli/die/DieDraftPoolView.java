package it.polimi.ingsw.client.view.cli.die;

import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * This class identify the list of die that can be place in each turn.
 */
public class DieDraftPoolView {


    /**
     * The list of dice contained.
     */
    private List<DieView> dice;


    public DieDraftPoolView(List<SetUpInformationUnit> draft){
        this.dice = new ArrayList<>();

        for(SetUpInformationUnit die: draft)
            dice.add(new DieView(die.getColor(),die.getValue()));
    }

    /**
     * This method create a draft pool in a string format with: DIE (number colored)
     * @return The draft pool in string format.
     */
    public String diceDraftToString(){
        StringBuilder diceDraft = new StringBuilder("\nRISERVA:");
        diceDraft.append("|");

        for(DieView d : this.dice)
            diceDraft.append(" ").append(d.toStringDie()).append(" |");

        return diceDraft.toString();
    }

    public List<DieView> getDice() {
        return dice;
    }
}
