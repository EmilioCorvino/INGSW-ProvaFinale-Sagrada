package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.view.cli.InputOutputManager;

public class ViewWindowPatternCard {

    /**
     * This object manage the input output communication with the user.
     */
    private InputOutputManager inputOutputManager;

    private int idMap;
    private int difficulty;

    private static final int MAX_COL = 5;
    private static final int MAX_ROW = 4;

    private ViewCell[][] glassWindow;


    public ViewWindowPatternCard(){
        this.inputOutputManager = new InputOutputManager();
        this.glassWindow = new ViewCell[MAX_ROW][MAX_COL];
    }

    /**
     * This method print the wp
     */
    public void printWp(){
        inputOutputManager.print("\nMAPPA: ");
        inputOutputManager.print(wpToString());
    }

    /**
     * This method create a wp in a string format with: DIE (number colored); COLOR RESTRICTION (Letter colored); VALUE RESTRICTION (number not colored)
     * @return The wp in string format
     */
    private String wpToString(){
        String wp = new String();

        for(int i = 0; i < glassWindow.length; i++) {
            for (int j = 0; j < glassWindow[i].length; j++) {
                wp += "| " + glassWindow[i][j].toStringCell() + " ";
            }
            wp += "|\n";
        }
        return wp;
    }
}
