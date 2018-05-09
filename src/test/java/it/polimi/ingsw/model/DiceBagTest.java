package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DiceBagTest {

    /**
     * This test check if at the construction the dice bag is populated with the correct number of die for each color.
     */
    @Test
    public void checkPopulation(){
        DiceBag diceBag = new DiceBag();

        int red = 0;
        int blue = 0;
        int purple = 0;
        int yellow = 0;
        int green = 0;

        for(Die d : diceBag.getAvailableDice()){
            if (d.getDieColor() == Color.RED)
                red ++;
            if (d.getDieColor() == Color.BLUE)
                blue ++;
            if (d.getDieColor() == Color.PURPLE)
                purple ++;
            if (d.getDieColor() == Color.YELLOW)
                yellow ++;
            if (d.getDieColor() == Color.GREEN)
                green ++;
        }

        assertEquals(diceBag.getDieNumberForEachColor()*diceBag.getNumberOfColors(), diceBag.getAvailableDice().size());

        assertEquals(diceBag.getDieNumberForEachColor(), red);
        assertEquals(diceBag.getDieNumberForEachColor(), blue);
        assertEquals(diceBag.getDieNumberForEachColor(), purple);
        assertEquals(diceBag.getDieNumberForEachColor(), yellow);
        assertEquals(diceBag.getDieNumberForEachColor(), green);
    }

    /**
     * This test verify if the extraction effective remove a die from the list, so the list contains one die less.
     * And it also verify if the dice inside tha bag, with the same color of the die extracted, are one less.
     */
    @Test
    public void extractDie() {
        DiceBag diceBag = new DiceBag();

        int nDiceSameColor = 0;

        Die die = diceBag.extract();

        assertTrue((diceBag.getDieNumberForEachColor()*diceBag.getNumberOfColors() - 1) == diceBag.getAvailableDice().size());

        for (Die d : diceBag.getAvailableDice())
            if(die.getDieColor() == d.getDieColor())
                nDiceSameColor ++;

        assertEquals(diceBag.getDieNumberForEachColor() - 1, nDiceSameColor);
    }
}