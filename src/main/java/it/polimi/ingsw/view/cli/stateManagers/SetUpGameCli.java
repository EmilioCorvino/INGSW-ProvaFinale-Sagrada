package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedDraftpool;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.view.cli.InputOutputManager;
import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages all the interaction during the game's state of set up.
 */
public class SetUpGameCli {

    InputOutputManager inputOutputManager = new InputOutputManager();

    public int showMapsToChoose(List<SimplifiedWindowPatternCard> listWp){
        List<WindowPatternCardView> cards = new ArrayList<>();
        int idChosen;

        for (SimplifiedWindowPatternCard swp : listWp)
            cards.add(new WindowPatternCardView(swp));

        inputOutputManager.print("Scegli la WP desiderata tra: ");
        for (WindowPatternCardView wp : cards)
            wp.printWp();
        idChosen = this.getIdChosen();
        while (idChosen!= cards.get(0).getIdMap() || idChosen!=cards.get(1).getIdMap() || idChosen!=cards.get(2).getIdMap() || idChosen!=cards.get(3).getIdMap()){
            inputOutputManager.print("Id non presente!");
            idChosen = this.getIdChosen();
        }
        return idChosen;
    }

    public void showCommonBoard(DieDraftPoolView draftPool, WindowPatternCardView wp){
        draftPool.printDraftPool();
        wp.printWp();

    }

    public int choseDraftDie(DieDraftPoolView draftPoolView){
        draftPoolView.printDraftPool();
        return Integer.parseInt(inputOutputManager.askInformation("Inserisci l'indice del dado che vuoi piazzare: "));
    }

    public int choseCellWp(WindowPatternCardView wp){
        wp.printWp();
        inputOutputManager.print("Inserisci le coordinate della cella in cui vuoi inserire il dado.");
        int row = Integer.parseInt(inputOutputManager.askInformation("Riga: "));
        int col = Integer.parseInt(inputOutputManager.askInformation("Colonna: "));

        return row*WindowPatternCardView.MAX_COL+col;
    }

    public void makeMove(DieDraftPoolView draft, WindowPatternCardView wp, SetUpInformationUnit unit){
        unit.setColor(draft.getDice().get(this.choseDraftDie(draft)).getDieColor());
        unit.setValue(draft.getDice().get(this.choseDraftDie(draft)).getDieValue());
        unit.setIndex(this.choseCellWp(wp));
    }

    public int showCommand(){
        return Integer.parseInt(inputOutputManager.askInformation("Scegli il comando:\n1-Piazzamento\n2-Uso Tool\n3-Visualizza plance altri giocatori\n4-Visualizza obiettivi pubblici\n5-Visualizza carte strumento\n6-Passa)"));
    }

    public int getIdChosen(){
        return Integer.parseInt(inputOutputManager.askInformation("Inserire l'id della mappa scelta: "));
    }
}
