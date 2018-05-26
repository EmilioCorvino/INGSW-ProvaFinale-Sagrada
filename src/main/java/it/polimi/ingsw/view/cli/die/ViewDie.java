package it.polimi.ingsw.view.cli.die;

public class ViewDie {

    private ViewColor dieColor;

    private int dieValue;

    public ViewDie(ViewColor dieColor, int dieValue){
        this.dieColor = dieColor;
        this.dieValue = dieValue;
    }

    public String toStringDie(){
        return "\033["+this.dieColor.getColorNumber()+";1m"+dieValue+"\033[0m";
    }
}
