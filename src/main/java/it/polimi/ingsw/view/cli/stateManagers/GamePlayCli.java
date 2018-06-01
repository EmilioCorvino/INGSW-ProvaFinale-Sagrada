package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.view.cli.InputOutputManager;
import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.die.DieView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.io.Serializable;
/**
 * This class manages all the interaction during the game play state.
 */
public class GamePlayCli implements Serializable {

    private transient InputOutputManager inputOutputManager = new InputOutputManager();

    /**
     *
     * @param draftPoolView
     * @return
     */
    private int choseDraftDie(DieDraftPoolView draftPoolView){
        draftPoolView.printDraftPool();

        int index = Integer.parseInt(inputOutputManager.askInformation("Inserisci l'indice del dado che vuoi piazzare: "));

        while(index < 1 || index > draftPoolView.getDice().size())
                index = Integer.parseInt(inputOutputManager.askInformation("Errore: inserire un valore tra (1-"+draftPoolView.getDice().size()+"): "));
        return index;
    }

    /**
     *
     * @param wp
     * @return
     */
    private int choseCellWp(WindowPatternCardView wp){
        wp.printWp();
        inputOutputManager.print("Inserisci le coordinate della cella in cui vuoi inserire il dado.");

        int row = Integer.parseInt(inputOutputManager.askInformation("Riga: "));
        while (row < 1 || row > WindowPatternCardView.MAX_ROW)
            row = Integer.parseInt(inputOutputManager.askInformation("Errore: Valore non supportato, inserire un valore tra (1-"+WindowPatternCardView.MAX_ROW+"): "));

        int col = Integer.parseInt(inputOutputManager.askInformation("Colonna: "));
        while (col < 1 || col > WindowPatternCardView.MAX_COL)
            col = Integer.parseInt(inputOutputManager.askInformation("Errore: Valore non supportato, inserire un valore tra (1-"+WindowPatternCardView.MAX_COL+"): "));

        return row*WindowPatternCardView.MAX_COL+col;
    }

    /**
     *
     * @param draft
     * @param wp
     * @param unit
     */
    public void getPlacementInfo(DieDraftPoolView draft, WindowPatternCardView wp, SetUpInformationUnit unit){
        unit.setColor(draft.getDice().get(this.choseDraftDie(draft)).getDieColor());
        unit.setValue(draft.getDice().get(this.choseDraftDie(draft)).getDieValue());
        unit.setIndex(this.choseCellWp(wp));
    }

    /**
     *
     * @return
     */
    public int showCommands(){
        inputOutputManager.print("E' il tuo turno!");

        int commandChosen = Integer.parseInt(inputOutputManager.askInformation("Scegli il comando:" +
                "\n1-Piazzamento\n2-Uso Tool\n3-Visualizza mappe altri giocatori\n" +
                "4-Visualizza obiettivi pubblici\n5-Visualizza carte strumento\n6-Passa)"));

        while(commandChosen < 1 || commandChosen > 6)
            commandChosen = Integer.parseInt(inputOutputManager.askInformation("Errore: Scelta non supportata, inserisci un valore tra (1-6)"));

        return commandChosen;
    }

    /**
     *
     * @param draft
     * @param unit
     */
    public void updateDraft(DieDraftPoolView draft, SetUpInformationUnit unit){
        for (DieView die : draft.getDice())
            if (die.getDieColor().equals(unit.getColor()) && die.getDieValue() == unit.getValue()){
                draft.getDice().remove(die);
                return;
        }

    }

    /**
     *
     * @param wp
     * @param unit
     */
    public void updateWp(WindowPatternCardView wp, SetUpInformationUnit unit){
        wp.getGlassWindow()[unit.getIndex()/WindowPatternCardView.MAX_COL][unit.getIndex() % WindowPatternCardView.MAX_COL].setDie(new DieView(unit.getColor(), unit.getValue()));
    }


}
