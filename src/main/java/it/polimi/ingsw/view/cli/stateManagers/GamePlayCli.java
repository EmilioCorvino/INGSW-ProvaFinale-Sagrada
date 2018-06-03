package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.view.cli.InputOutputManager;
import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.die.DieView;
import it.polimi.ingsw.view.cli.die.PlayerView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
        while (row < 0 || row > WindowPatternCardView.MAX_ROW - 1)
            row = Integer.parseInt(inputOutputManager.askInformation("Errore: Valore non supportato, inserire un valore tra (0-"+(WindowPatternCardView.MAX_ROW-1)+"): "));

        int col = Integer.parseInt(inputOutputManager.askInformation("Colonna: "));
        while (col < 0 || col > WindowPatternCardView.MAX_COL - 1)
            col = Integer.parseInt(inputOutputManager.askInformation("Errore: Valore non supportato, inserire un valore tra (0-"+(WindowPatternCardView.MAX_COL-1)+"): "));

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
     * This method allow the player to choose the command.
     * @return: the command chosen.
     */
    public int showCommand(){
        inputOutputManager.print("E' il tuo turno!");

        int commandChosen = Integer.parseInt(inputOutputManager.askInformation("Scegli il comando:" +
                "\n\t 1 - Piazzamento\n\t 2 - Uso Tool\n\t 3 - Visualizza mappe altri giocatori" +
                "\n\t 4 - Visualizza carte obiettivo pubblico\n\t 5 - Visualizza carte strumento\n\t 6 - Passa)"));

        while(commandChosen < 1 || commandChosen > 6)
            commandChosen = Integer.parseInt(inputOutputManager.askInformation("Errore: Scelta non supportata, inserisci un valore tra (1-6)"));

        return commandChosen;
    }

    public void showNotMyTurnCommand(){
       inputOutputManager.print("Scegli il comando:" +
                "\n\t 1 - Visualizza mappe altri giocatori" +
                "\n\t 2 - Visualizza carte obiettivo pubblico\n\t 3 - Visualizza carte strumento)");
    }


    /**
     *
     * @param wp
     * @param unit
     */
    public void updateWp(WindowPatternCardView wp, SetUpInformationUnit unit){
        wp.getGlassWindow()[unit.getIndex()/WindowPatternCardView.MAX_COL][unit.getIndex() % WindowPatternCardView.MAX_COL].setDie(new DieView(unit.getColor(), unit.getValue()));
    }

    public void printPubObj(List<String> cards){
        int index = 1;

        inputOutputManager.print("Carte obiettivo pubblico: ");
        for (String s : cards){
            inputOutputManager.print("\t - " + index + ": " + s);
            index ++;
        }
    }

    public void printTool(Map<String, Integer> cards){
        int index = 1;

        inputOutputManager.print("Carte strumento: ");
        for (Map.Entry<String,Integer> s : cards.entrySet()){
            inputOutputManager.print("\t - " + index + ": " + s.getKey() + " | Segnalini favore da usare: " + s.getValue());
            index ++;
        }
    }

    public void printAllWp(List<PlayerView> players){
        for(PlayerView p : players){
            inputOutputManager.print("Giocatore " + p.getUserName());
            p.getWp().printWp();
        }
    }
}
