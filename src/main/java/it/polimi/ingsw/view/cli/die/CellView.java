package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.model.Color;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * This class identify a cell
 */
public class CellView {

    private DieView die;

    private Color defaultColorRestriction;

    private int defaultValueRestriction;

    CellView(Color defaultColorRestriction, int defaultValueRestriction){
        this.defaultColorRestriction = defaultColorRestriction;
        this.defaultValueRestriction = defaultValueRestriction;
    }

    String toStringCell(){
        if(die != null)
            return die.toStringDie();
        else if (defaultColorRestriction != Color.BLANK)
            return "\033["+defaultColorRestriction.getColorNumber()+"m"+defaultColorRestriction.getId()+"\033[0m";
        else if(defaultValueRestriction != 0)
            return ""+defaultValueRestriction;
        else
            return" ";
    }
    /*
    String toStringCell(){
        if(die != null)
            return die.toStringDie();
        else if (defaultColorRestriction != Color.BLANK)
            return ansi().fg(Ansi.Color.valueOf(defaultColorRestriction.toString())) + defaultColorRestriction.getId() + ansi().fg(Ansi.Color.DEFAULT);
        else if(defaultValueRestriction != 0)
            return ""+defaultValueRestriction;
        else
            return" ";
    }
    */

    public void setDie(DieView die) {
        this.die = die;
    }

    void setDefaultColorRestriction(Color defaultColorRestriction) {
        this.defaultColorRestriction = defaultColorRestriction;
    }

    void setDefaultValueRestriction(int defaultValueRestriction) {
        this.defaultValueRestriction = defaultValueRestriction;
    }
}
