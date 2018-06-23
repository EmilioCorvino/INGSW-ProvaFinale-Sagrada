package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.model.Color;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * This class identify a die in the view context.
 */
public class DieView {

    private Color dieColor;

    private int dieValue;

    public DieView(Color dieColor, int dieValue){
        this.dieColor = dieColor;
        this.dieValue = dieValue;
    }

    public Color getDieColor() {
        return dieColor;
    }

    public int getDieValue() {
        return dieValue;
    }

    public String toStringDie(){
        return "\033["+this.dieColor.getColorNumber()+";1m"+dieValue+"\033[0m";
        /*
        return ansi().fg(Ansi.Color.valueOf(dieColor.toString())) + "" + dieValue + ansi().fg(Ansi.Color.DEFAULT);
        */
    }
}
