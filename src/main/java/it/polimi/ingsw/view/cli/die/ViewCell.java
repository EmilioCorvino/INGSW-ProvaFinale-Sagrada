package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.model.Color;

public class ViewCell {

    private ViewDie die;

    private Color defaultColorRestriction;

    private Integer defaultValueRestriction;

    public String toStringCell(){
        if(die != null)
            return die.toStringDie();
        else if (defaultColorRestriction != null)
            return "\033["+defaultColorRestriction.getColorNumber()+"m"+defaultColorRestriction.getId()+"\033[0m";
        else if(defaultValueRestriction != null)
            return defaultValueRestriction.toString();
        else
            return" ";
    }

}
