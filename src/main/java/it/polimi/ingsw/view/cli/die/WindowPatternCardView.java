package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.view.cli.InputOutputManager;

/**
 * This class identify the window pattern card in the view.
 */
public class WindowPatternCardView {

    /**
     * This object manage the input output communication with the user.
     */
    private InputOutputManager inputOutputManager;

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
        this.inputOutputManager = new InputOutputManager();
        this.glassWindow = new CellView[MAX_ROW][MAX_COL];
        this.populateViewWP(sWP);
    }

    /**
     * This method print the wp
     */
    public void printWp(){
        inputOutputManager.print("\nMAPPA "+idMap+": ");
        inputOutputManager.print(wpToString());
        inputOutputManager.print("Difficoltà: " + difficulty);
    }

    /**
     * This method create a wp in a string format with: DIE (number colored); COLOR RESTRICTION (Letter colored); VALUE RESTRICTION (number not colored)
     * @return The wp in string format
     */
    private String wpToString(){
        StringBuilder wp = new StringBuilder();

        for(int i = 0; i < glassWindow.length; i++) {
            for (int j = 0; j < glassWindow[i].length; j++) {
                wp.append("| ").append(glassWindow[i][j].toStringCell()).append(" ");
            }
            wp.append("|\n");
        }
        return wp.toString();
    }

    /**
     * This method populate create an object view window pattern card with the info contained in simplified window pattern card.
     * @param sWP: the object with the info needed to populate the window pattern
     */
    private void populateViewWP(SimplifiedWindowPatternCard sWP){

        for (SetUpInformationUnit info : sWP.getInformationUnitList()) {
            this.setIdMap(sWP.getIdMap());
            this.setDifficulty(sWP.getDifficulty());
            this.getGlassWindow()[info.getIndex() / (WindowPatternCardView.MAX_COL)][info.getIndex() % (WindowPatternCardView.MAX_COL)].setDefaultColorRestriction(info.getColor());
            this.getGlassWindow()[info.getIndex() / (WindowPatternCardView.MAX_COL)][info.getIndex() % (WindowPatternCardView.MAX_COL)].setDefaultValueRestriction(info.getValue());
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
