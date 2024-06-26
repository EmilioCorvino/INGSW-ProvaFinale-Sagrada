package it.polimi.ingsw.server.model.cards.tool.effects.value;

import it.polimi.ingsw.common.Color;
import it.polimi.ingsw.server.model.die.Cell;
import it.polimi.ingsw.server.model.die.Die;
import it.polimi.ingsw.server.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.server.model.restrictions.ColorRestriction;
import it.polimi.ingsw.server.model.restrictions.IRestriction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AValueEffectTest {

    @Test
    public void checkExistingCellsToUse() {

        WindowPatternCard wp = new WindowPatternCard(3, 3);
        Die die = new Die(4, Color.BLUE);
        wp.createCopy();
        Cell[][] gw = wp.getGlassWindowCopy();

        for(int i=0; i< WindowPatternCard.MAX_ROW; i++)
            for(int j=0; j< WindowPatternCard.getMaxCol(); j++)
                gw[i][j].setContainedDie(new Die(3, Color.GREEN));

        AValueEffect effect = new OppositeValueEffect();
        assertFalse(effect.checkExistingCellsToUse(wp, die));


        WindowPatternCard wp1 = new WindowPatternCard(4, 5);
        wp1.createCopy();
        Cell[][] gw1 = wp1.getGlassWindowCopy();
        List<IRestriction> list = new ArrayList<>();
        list.add(new ColorRestriction(Color.PURPLE));

        gw1[3][0].setContainedDie(new Die(3, Color.GREEN));
        gw1[2][0].setRuleSetCell(list);
        gw1[3][1].setRuleSetCell(list);
        gw1[2][1].setContainedDie(new Die(3, Color.PURPLE));

        AValueEffect effect1 = new ChooseValueEffect();
        assertTrue(effect1.checkExistingCellsToUse(wp1, die));
    }
}