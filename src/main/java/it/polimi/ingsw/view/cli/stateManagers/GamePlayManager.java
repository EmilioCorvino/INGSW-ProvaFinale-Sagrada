package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.view.cli.generalManagers.InputOutputManager;
import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.die.DieView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * This class manages all the interaction during the game play state.
 */
public class GamePlayManager implements Serializable {

    private transient InputOutputManager inputOutputManager;

    public GamePlayManager(InputOutputManager inputOutputManager){
        this.inputOutputManager = inputOutputManager;
    }

    /**
     * This method ask the index of the die chosen in the draft, verifying if it is an int and if it is in bound.
     * @param draftPoolView: The draft Pool
     * @return: the index chosen.
     */
    private int choseDraftDie(DieDraftPoolView draftPoolView){
        draftPoolView.printDraftPool();

        String index = inputOutputManager.askInformation("Inserisci l'indice del dado che vuoi piazzare (da 0 a "+(draftPoolView.getDice().size()-1)+"): ");
        boolean validInput = Pattern.matches("\\d*", index);

        while(!validInput || Integer.parseInt(index) < 0 || Integer.parseInt(index) > draftPoolView.getDice().size()-1)
                index = inputOutputManager.askInformation("Errore: inserire un valore tra (0-"+(draftPoolView.getDice().size()-1)+"): ");
        return Integer.parseInt(index);
    }

    /**
     * This method ask to the user the row and the column of the cell where he want to place the die, and verifying if it is an int and if it is in bound.
     * @param wp
     * @return
     */
    private int choseCellWp(WindowPatternCardView wp){
        wp.printWp();
        inputOutputManager.print("Inserisci le coordinate della cella in cui vuoi inserire il dado.");

        String row = inputOutputManager.askInformation("Riga: ");
        boolean validRow = Pattern.matches("\\d*", row);

        while (!validRow || Integer.parseInt(row) < 0 || Integer.parseInt(row) > WindowPatternCardView.MAX_ROW - 1)
            row = inputOutputManager.askInformation("Errore: Valore non supportato, inserire un valore tra (0-"+(WindowPatternCardView.MAX_ROW-1)+"): ");

        String col = inputOutputManager.askInformation("Colonna: ");
        boolean validCol = Pattern.matches("\\d*", col);

        while (!validCol || Integer.parseInt(col) < 0 || Integer.parseInt(col) > WindowPatternCardView.MAX_COL - 1)
            col = inputOutputManager.askInformation("Errore: Valore non supportato, inserire un valore tra (0-"+(WindowPatternCardView.MAX_COL-1)+"): ");

        return Integer.parseInt(row) * WindowPatternCardView.MAX_COL + Integer.parseInt(col);
    }

    /**
     * This method ask all the information needed for a default placement
     * @param draft: the draft pool of the common board.
     * @param wp: the wp of the player connected.
     * @param unit: the unit that need to be fill with the placement information and that need to be send to the controller.
     */
    public void getPlacementInfo(DieDraftPoolView draft, WindowPatternCardView wp, SetUpInformationUnit unit){
        int index = this.choseDraftDie(draft);

        unit.setColor(draft.getDice().get(index).getDieColor());
        unit.setValue(draft.getDice().get(index).getDieValue());
        unit.setIndex(this.choseCellWp(wp));
    }

    /**
     * This method allow the player to choose the command.
     * @return the command chosen.
     */
    public int showCommand(){
        inputOutputManager.print("\nE' il tuo turno!");

        int commandChosen = Integer.parseInt(inputOutputManager.askInformation("Scegli il comando:" +
                "\n\t 1 - Piazzamento\n\t 2 - Uso Tool\n\t 3 - Visualizza mappe altri giocatori" +
                "\n\t 4 - Visualizza obiettivi pubblici\n\t 5 - Visualizza carte strumento\n\t 6 - Visualizza obiettivo privato\n\t 7 - Passa"));

        while(commandChosen < 0 || commandChosen > 7)
            commandChosen = Integer.parseInt(inputOutputManager.askInformation("Errore: Scelta non supportata, inserisci un valore tra (0-5)"));

        return commandChosen;
    }

    /**
     *
     * @param wp
     * @param unit
     */
    public void addOnWp(WindowPatternCardView wp, SetUpInformationUnit unit){
        wp.getGlassWindow()[unit.getIndex()/WindowPatternCardView.MAX_COL][unit.getIndex() % WindowPatternCardView.MAX_COL].setDie(new DieView(unit.getColor(), unit.getValue()));
    }

    public void removeOnWp(WindowPatternCardView wp, SetUpInformationUnit unit){
        wp.getGlassWindow()[unit.getIndex()/WindowPatternCardView.MAX_COL][unit.getIndex() % WindowPatternCardView.MAX_COL].removeDie();
    }

    public void addOnDraft(DieDraftPoolView draft, SetUpInformationUnit unit){
        draft.getDice().add(new DieView(unit.getColor(), unit.getValue()));
    }

}
