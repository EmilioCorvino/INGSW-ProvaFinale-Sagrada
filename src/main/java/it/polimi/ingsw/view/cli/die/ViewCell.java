package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.model.Color;

public class ViewCell {

    private ViewDie die;

    private Color defaultColorRestriction;

    private int defaultValueRestriction;

    public String toStringCell(){
        if(die != null)
            return die.toStringDie();
        else if (defaultColorRestriction != null)
            return "\033["+defaultColorRestriction.getColorNumber()+"m"+defaultColorRestriction.getId()+"\033[0m";
        else if(defaultValueRestriction != 0)
            return ""+defaultValueRestriction;
        else
            return" ";
    }

    public void setDie(ViewDie die) {
        this.die = die;
    }

    public void setDefaultColorRestriction(Color defaultColorRestriction) {
        this.defaultColorRestriction = defaultColorRestriction;
    }

    public void setDefaultValueRestriction(int defaultValueRestriction) {
        this.defaultValueRestriction = defaultValueRestriction;
    }
}
