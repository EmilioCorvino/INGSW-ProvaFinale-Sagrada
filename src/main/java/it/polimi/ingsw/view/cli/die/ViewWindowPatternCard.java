package it.polimi.ingsw.view.cli.die;

public class ViewWindowPatternCard {

    private int idMap;
    private int difficulty;

    private static final int MAX_COL = 5;
    private static final int MAX_ROW = 4;

    private ViewCell[][] glassWindow = new ViewCell[MAX_ROW][MAX_COL];

    public void printWp(){

        System.out.println("\nMAPPA: ");
        this.printBorder("_");
        this.wpToString();
        this.printBorder("-");
    }

    public void wpToString(){
        String wp = new String();

        for(int i = 0; i < glassWindow.length; i++) {
            for (int j = 0; j < glassWindow[i].length; j++) {
                wp += "| " + glassWindow[i][j].printCell() + " ";
            }
            System.out.println(wp + "|");
            wp = "";
        }
    }

    public void printBorder(String typeBoarder){
        String boarder = new String();

        for(int k = 0; k < glassWindow.length; k++)
            boarder += typeBoarder + typeBoarder + typeBoarder + typeBoarder + typeBoarder;

        System.out.println(boarder);
    }
}
