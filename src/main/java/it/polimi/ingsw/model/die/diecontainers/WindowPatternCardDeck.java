package it.polimi.ingsw.model.die.diecontainers;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.restrictions.ColorRestriction;
import it.polimi.ingsw.model.restrictions.ValueRestriction;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class WindowPatternCardDeck {


    public void parseWP() {

        List<Cell> restrictedCell = new ArrayList<>();
        restrictedCell.add(createValuedCell(0,0,4));
        restrictedCell.add(createValuedCell(0,2,2));
        restrictedCell.add(createValuedCell(0,3,5));
        restrictedCell.add(createColoredCell(0,4,Color.GREEN));
        restrictedCell.add(createValuedCell(1,2,6));
        restrictedCell.add(createColoredCell(1,3,Color.GREEN));
        restrictedCell.add(createValuedCell(1,4,2));
        restrictedCell.add(createValuedCell(2,1,3));
        restrictedCell.add(createColoredCell(2,2,Color.GREEN));
        restrictedCell.add(createValuedCell(2,3,4));
        restrictedCell.add(createValuedCell(3,0,5));
        restrictedCell.add(createColoredCell(3,1,Color.GREEN));
        restrictedCell.add(createValuedCell(3,2,1));

        Gson gson = new Gson();
        WindowPatternCard wp = new WindowPatternCard(1,5,restrictedCell);

        gson.toJson(wp, System.out);

    }


    public Cell createColoredCell(int x, int y, Color color) {
        return new Cell(x, y, new ColorRestriction(color));
    }

    public Cell createValuedCell(int x, int y, int restriction){
        return new Cell(x, y, new ValueRestriction(restriction));
    }
}
    /*
    public List<WindowPatternCard> getDeck() {
        return (List<WindowPatternCard>) this.deck;
    }
    */

    /*
    public void parseDeck() {
        Gson gson = new Gson();
        try(Reader file = new FileReader("./src/main/resources/cards/windowPatternCard.json")){
            this.deck = gson.fromJson(file, WindowPatternCard);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    */


