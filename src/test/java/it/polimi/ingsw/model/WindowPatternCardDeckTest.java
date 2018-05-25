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


    public WindowPatternCard createWP() {

        List<Cell> restrictedCell = new ArrayList<>();
        restrictedCell.add(createValuedCell(0, 0, 4));
        restrictedCell.add(createValuedCell(0, 2, 2));
        restrictedCell.add(createValuedCell(0, 3, 5));
        restrictedCell.add(createColoredCell(0, 4, Color.GREEN));
        restrictedCell.add(createValuedCell(1, 2, 6));
        restrictedCell.add(createColoredCell(1, 3, Color.GREEN));
        restrictedCell.add(createValuedCell(1, 4, 2));
        restrictedCell.add(createValuedCell(2, 1, 3));
        restrictedCell.add(createColoredCell(2, 2, Color.GREEN));
        restrictedCell.add(createValuedCell(2, 3, 4));
        restrictedCell.add(createValuedCell(3, 0, 5));
        restrictedCell.add(createColoredCell(3, 1, Color.GREEN));
        restrictedCell.add(createValuedCell(3, 2, 1));

        return new WindowPatternCard(1, 5, restrictedCell);

    }

    public Cell createColoredCell(int x, int y, Color color) {
        return new Cell(x, y, new ColorRestriction(color));
    }

    public Cell createValuedCell(int x, int y, int restriction) {
        return new Cell(x, y, new ValueRestriction(restriction));
    }
    @Test
    public void deParsingAndParsingTest(){
        WindowPatternCardDeck windowPatternCardDeck = new WindowPatternCardDeck();
        windowPatternCardDeck.parseDeck();

        assertEquals(this.createWP(), windowPatternCardDeck.getDeck().get(0));
    }

}