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

    private int choseDraftDie(DieDraftPoolView draftPoolView){
        draftPoolView.printDraftPool();
        return Integer.parseInt(inputOutputManager.askInformation("Inserisci l'indice del dado che vuoi piazzare: "));
    }

    private int choseCellWp(WindowPatternCardView wp){
        wp.printWp();
        inputOutputManager.print("Inserisci le coordinate della cella in cui vuoi inserire il dado.");
        int row = Integer.parseInt(inputOutputManager.askInformation("Riga: "));
        int col = Integer.parseInt(inputOutputManager.askInformation("Colonna: "));

        return row*WindowPatternCardView.MAX_COL+col;
    }

    public void getPlacementInfo(DieDraftPoolView draft, WindowPatternCardView wp, SetUpInformationUnit unit){
        unit.setColor(draft.getDice().get(this.choseDraftDie(draft)).getDieColor());
        unit.setValue(draft.getDice().get(this.choseDraftDie(draft)).getDieValue());
        unit.setIndex(this.choseCellWp(wp));
    }

    public int showCommands(){
        inputOutputManager.print("E' il tuo turno!");
        return Integer.parseInt(inputOutputManager.askInformation("Scegli il comando:\n1-Piazzamento\n2-Uso Tool\n3-Visualizza plance altri giocatori\n4-Visualizza obiettivi pubblici\n5-Visualizza carte strumento\n6-Passa)"));
    }

    public void updateDraft(DieDraftPoolView draft, SetUpInformationUnit unit){
        for (DieView die : draft.getDice())
            if (die.getDieColor().equals(unit.getColor()) && die.getDieValue() == unit.getValue()){
                draft.getDice().remove(die);
                return;
        }

    }

    public void updateWp(WindowPatternCardView wp, SetUpInformationUnit unit){
        wp.getGlassWindow()[unit.getIndex()/WindowPatternCardView.MAX_COL][unit.getIndex() % WindowPatternCardView.MAX_COL].setDie(new DieView(unit.getColor(), unit.getValue()));
    }


}
