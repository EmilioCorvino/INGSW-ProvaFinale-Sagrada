package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.model.Color;

public class DieView {

    private Color dieColor;

    private int dieValue;

    public DieView(Color dieColor, int dieValue){
        this.dieColor = dieColor;
        this.dieValue = dieValue;
    }

    public String toStringDie(){
        return "\033["+this.dieColor.getColorNumber()+";1m"+dieValue+"\033[0m";
    }
}
