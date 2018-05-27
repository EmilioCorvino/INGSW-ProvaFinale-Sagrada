package it.polimi.ingsw.view.cli.die;

public class ViewCell {

    private ViewDie die;

    private ViewColor defaultColorRestriction;

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
