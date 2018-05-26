package it.polimi.ingsw.view.cli.die;

public class ViewCell {

    private ViewDie die;

    private ViewColorRestriction defaultColorRestriction;

    private ViewValueRestriction defaultValueRestriction;

    public String printCell(){
        if(die != null)
            return die.printDie();
        else if (defaultColorRestriction != null)
            return "\033["+defaultColorRestriction.getColor().getColorNumber()+"m"+defaultColorRestriction.getColor().getId()+"\033[0m";
        else if(defaultValueRestriction != null)
            return defaultValueRestriction.getValue().toString();
        else
            return" ";
    }

}
