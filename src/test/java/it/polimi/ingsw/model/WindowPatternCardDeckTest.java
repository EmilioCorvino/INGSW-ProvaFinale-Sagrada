package it.polimi.ingsw.model;

import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCardDeck;
import it.polimi.ingsw.model.restrictions.ColorRestriction;
import it.polimi.ingsw.model.restrictions.ValueRestriction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class WindowPatternCardDeckTest {

    private Cell createColoredCell(int x, int y, Color color) {
        return new Cell(x, y, new ColorRestriction(color));
    }

    private Cell createValuedCell(int x, int y, int restriction) {
        return new Cell(x, y, new ValueRestriction(restriction));
    }
    @Test
    public void parsingTest(){

        List<Cell> restrictedCell1 = new ArrayList<>();
        restrictedCell1.add(createValuedCell(0, 0, 4));
        restrictedCell1.add(createValuedCell(0, 2, 2));
        restrictedCell1.add(createValuedCell(0, 3, 5));
        restrictedCell1.add(createColoredCell(0, 4, Color.GREEN));
        restrictedCell1.add(createValuedCell(1, 2, 6));
        restrictedCell1.add(createColoredCell(1, 3, Color.GREEN));
        restrictedCell1.add(createValuedCell(1, 4, 2));
        restrictedCell1.add(createValuedCell(2, 1, 3));
        restrictedCell1.add(createColoredCell(2, 2, Color.GREEN));
        restrictedCell1.add(createValuedCell(2, 3, 4));
        restrictedCell1.add(createValuedCell(3, 0, 5));
        restrictedCell1.add(createColoredCell(3, 1, Color.GREEN));
        restrictedCell1.add(createValuedCell(3, 2, 1));

        WindowPatternCard wp1 = new WindowPatternCard(1, 5,restrictedCell1);

        List<Cell> restrictedCell2 = new ArrayList<>();
        restrictedCell2.add(createColoredCell(0,0,Color.YELLOW));
        restrictedCell2.add(createColoredCell(0,1,Color.BLUE));
        restrictedCell2.add(createValuedCell(0,4, 1));
        restrictedCell2.add(createColoredCell(1,0,Color.GREEN));
        restrictedCell2.add(createValuedCell(1,2,5));
        restrictedCell2.add(createValuedCell(1,4,4));
        restrictedCell2.add(createValuedCell(2,0,3));
        restrictedCell2.add(createColoredCell(2,2,Color.RED));
        restrictedCell2.add(createColoredCell(2,4,Color.GREEN));
        restrictedCell2.add(createValuedCell(3,0,2));
        restrictedCell2.add(createColoredCell(3,3, Color.BLUE));
        restrictedCell2.add(createColoredCell(3,4,Color.YELLOW));

        WindowPatternCard wp2 = new WindowPatternCard(1, 5,restrictedCell2);

        WindowPatternCardDeck windowPatternCardDeck = new WindowPatternCardDeck();
        windowPatternCardDeck.parseDeck();


        for(int i = 0; i < wp1.MAX_ROW; i++) {
            for (int j = 0; j < wp1.MAX_COL; j++) {
                if (wp1.getGlassWindow()[i][j].getDefaultColorRestriction() != null)
                    assertEquals(wp1.getGlassWindow()[i][j].getDefaultColorRestriction().getColor(),
                            windowPatternCardDeck.getAvailableWP().get(0).getGlassWindow()[i][j].getDefaultColorRestriction().getColor());
                if (wp1.getGlassWindow()[i][j].getDefaultValueRestriction() != null)
                    assertEquals(wp1.getGlassWindow()[i][j].getDefaultValueRestriction().getValue(),
                            windowPatternCardDeck.getAvailableWP().get(0).getGlassWindow()[i][j].getDefaultValueRestriction().getValue());
            }
        }

        assertEquals(wp2.getGlassWindow()[2][2].getDefaultColorRestriction().getColor(), windowPatternCardDeck.getAvailableWP().get(2).getGlassWindow()[2][2].getDefaultColorRestriction().getColor());

        for(int i = 0; i < wp2.MAX_ROW; i++) {
            for (int j = 0; j < wp2.MAX_COL; j++) {
                if (wp2.getGlassWindow()[i][j].getDefaultColorRestriction() != null)
                    assertEquals(wp2.getGlassWindow()[i][j].getDefaultColorRestriction().getColor(),
                            windowPatternCardDeck.getAvailableWP().get(2).getGlassWindow()[i][j].getDefaultColorRestriction().getColor());
                if (wp2.getGlassWindow()[i][j].getDefaultValueRestriction() != null)
                    assertEquals(wp2.getGlassWindow()[i][j].getDefaultValueRestriction().getValue(),
                            windowPatternCardDeck.getAvailableWP().get(2).getGlassWindow()[i][j].getDefaultValueRestriction().getValue());
            }
        }

    }

    @Test
    public void testParsedListSize(){

        WindowPatternCardDeck windowPatternCardDeck = new WindowPatternCardDeck();
        windowPatternCardDeck.parseDeck();

        assertEquals(24, windowPatternCardDeck.getAvailableWP().size());
    }
}