package it.polimi.ingsw.client.view.cli.die;

import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.simplifiedview.SimplifiedWindowPatternCard;

/**
 * This class identify the window pattern card in the view.
 */
public class WindowPatternCardView {

    private int idMap;
    private int difficulty;

    public static final int MAX_COL = 5;
    public static final int MAX_ROW = 4;

    private CellView[][] glassWindow;

    /**
     * This constructor build a window pattern card from the information given from the controller.
     * @param sWP: The set of useful information to identify a window pattern card.
     */
    public WindowPatternCardView(SimplifiedWindowPatternCard sWP){
        this.glassWindow = new CellView[MAX_ROW][MAX_COL];
        this.populateViewWP(sWP);
    }

    /**
     * This method create a wp in a string format with: DIE (number colored); COLOR RESTRICTION (Letter colored); VALUE RESTRICTION (number not colored)
     * @return The wp in string format
     */
    public String wpToString(){
        StringBuilder wp = new StringBuilder("\nMAPPA "+idMap +": \n");

        for(int i = 0; i < glassWindow.length; i++) {
            wp.append(" ").append(i).append("  ");
            for (int j = 0; j < glassWindow[i].length; j++) {
                wp.append("| ").append(this.glassWindow[i][j].toStringCell()).append(" ");
            }
            wp.append("|\n");
        }
        wp.append("      0   1   2   3   4");
        wp.append("\nDifficoltà: ").append(difficulty);
        return wp.toString();
    }

    /**
     * This method populate create an object view window pattern card with the info contained in simplified window pattern card.
     * @param sWP: the object with the info needed to populate the window pattern
     */
    private void populateViewWP(SimplifiedWindowPatternCard sWP){
        this.setIdMap(sWP.getIdMap());
        this.setDifficulty(sWP.getDifficulty());
        for (SetUpInformationUnit info : sWP.getInformationUnitList()) {
            this.glassWindow[info.getDestinationIndex() / (WindowPatternCardView.MAX_COL)]
                    [info.getDestinationIndex() % (WindowPatternCardView.MAX_COL)] =
                    new CellView(info.getColor(), info.getValue());
        }
    }

    public CellView[][] getGlassWindow() {
        return glassWindow;
    }

    private void setIdMap(int idMap) {
        this.idMap = idMap;
    }

    private void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getIdMap() {
        return idMap;
    }
}
