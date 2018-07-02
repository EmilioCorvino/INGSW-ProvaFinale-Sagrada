package it.polimi.ingsw.model.cards.tool.effects.ignore;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MoveWithRestrictionsEffectTest {

    @Test
    public void isGlassWindowEmpty() {
        WindowPatternCard wp = new WindowPatternCard(0, 3);
        wp.createCopy();

        Die die = new Die(5, Color.BLUE);
        wp.setDesiredCell(new Cell(0,1));
        wp.addDieToCopy(die);

        MoveWithRestrictionsEffect effect = new MoveWithRestrictionsEffect();

        assertFalse(effect.hasGlassWindowLessThanTwoDice(wp.getGlassWindowCopy()));
    }

    @Test
    public void checkMoveAvailability() {

        WindowPatternCard wp = new WindowPatternCard(0, 3);
        wp.createCopy();

        Die die = new Die(5, Color.BLUE);
        wp.setDesiredCell(new Cell(0,1));
        wp.addDieToCopy(die);

        Die die2 = new Die(5, Color.GREEN);
        wp.setDesiredCell(new Cell(1, 2));
        wp.addDieToCopy(die2);

        SetUpInformationUnit info = new SetUpInformationUnit();
        info.setSourceIndex(1);
        info.setDestinationIndex(7);

        MoveWithRestrictionsEffect effect = new MoveWithRestrictionsEffect();

        assertFalse(effect.checkMoveAvailability(wp.getGlassWindowCopy(), info));
        assertEquals("La cella destinazione è piena", effect.getInvalidMoveMessage());

    }
}