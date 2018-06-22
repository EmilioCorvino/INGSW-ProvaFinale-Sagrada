package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.view.cli.die.RoundTrackView;
import it.polimi.ingsw.view.cli.generalmanagers.InputOutputManager;
import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.die.DieView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.util.regex.Pattern;

/**
 * This class manages all the interaction during the game play state.
 */
public class GamePlayManager{

    private InputOutputManager inputOutputManager;

    public GamePlayManager(InputOutputManager inputOutputManager){
        this.inputOutputManager = inputOutputManager;
    }

    /**
     * This method ask the index of the die chosen in the draft, verifying if it is an int and if it is in bound.
     * @param draftPoolView: The draft Pool
     * @return: the index chosen.
     */
    public int choseDraftDie(DieDraftPoolView draftPoolView){

        String index = inputOutputManager.askInformation("Inserisci l'indice del dado desiderato (da 0 a "+(draftPoolView.getDice().size()-1)+"): ");
        boolean validInput = Pattern.matches("\\d*", index);

        while(!validInput || Integer.parseInt(index) < 0 || Integer.parseInt(index) > draftPoolView.getDice().size()-1) {
            index = inputOutputManager.askInformation("Errore: inserire un valore tra (0-" + (draftPoolView.getDice().size() - 1) + "): ");
            validInput = Pattern.matches("\\d*", index);
        }
        return Integer.parseInt(index);
    }

    /**
     * This method ask to the user the row and the column of the cell where he want to place the die, and verifying if it is an int and if it is in bound.
     * @return the modular conversion of raw and column.
     */
    public int choseCellWp(){
        inputOutputManager.print("Inserisci le coordinate della cella desiderata.");

        String row = inputOutputManager.askInformation("Riga: ");
        boolean validRow = Pattern.matches("\\d*", row);

        while (!validRow || Integer.parseInt(row) < 0 || Integer.parseInt(row) > WindowPatternCardView.MAX_ROW - 1) {
            row = inputOutputManager.askInformation("Errore: Valore non supportato, inserire un valore tra (0-" + (WindowPatternCardView.MAX_ROW - 1) + "): ");
            validRow = Pattern.matches("\\d*", row);
        }

        String col = inputOutputManager.askInformation("Colonna: ");
        boolean validCol = Pattern.matches("\\d*", col);

        while (!validCol || Integer.parseInt(col) < 0 || Integer.parseInt(col) > WindowPatternCardView.MAX_COL - 1) {
            col = inputOutputManager.askInformation("Errore: Valore non supportato, inserire un valore tra (0-" + (WindowPatternCardView.MAX_COL - 1) + "): ");
            validCol = Pattern.matches("\\d*", col);
        }
        return Integer.parseInt(row) * WindowPatternCardView.MAX_COL + Integer.parseInt(col);
    }

    /**
     * This method fill a set up info unit with the information of the position of the die chosen in the round track
     * @param roundTrack: an instance of the round track
     * @param informationUnit: the information unit to fill.
     */
    public void choseRoundDie(RoundTrackView roundTrack, SetUpInformationUnit informationUnit){

        String idRound = inputOutputManager.askInformation("Inserisci il numero del round in cui c'e' il dado desiderato: ");
        boolean validRound = Pattern.matches("\\d*", idRound);

        while(!validRound || Integer.parseInt(idRound) < 0 || Integer.parseInt(idRound) > 10) {
            idRound = inputOutputManager.askInformation("Errore: Round non presente, inserire valore tra (1-10) ");
            validRound = Pattern.matches("\\d*", idRound);
        }

        int roundChosen = Integer.parseInt(idRound);
        informationUnit.setExtraParam(roundChosen);

        String idDie = inputOutputManager.askInformation("Inserisci la posizione del dado desiderato (0-"+ roundTrack.getAvailableDice().get(roundChosen).size()+"): ");
        boolean validId = Pattern.matches("\\d*", idDie);

        while(!validId || Integer.parseInt(idDie) < 0 || Integer.parseInt(idDie) > roundTrack.getAvailableDice().get(roundChosen).size()) {
            idDie = inputOutputManager.askInformation("Errore: indice errato, inserire indice tra (0-" + roundTrack.getAvailableDice().get(roundChosen).size() + "): ");
            validId = Pattern.matches("\\d*", idDie);
        }

        int dieChosen = Integer.parseInt(idDie);
        informationUnit.setOffset(dieChosen);

    }

    /**
     * This method ask all the information needed for a default placement
     * @param draft: the draft pool of the common board.
     * @param wp: the wp of the player connected.
     * @param unit: the unit that need to be fill with the placement information and that need to be send to the controller.
     */
    public void getPlacementInfo(DieDraftPoolView draft, WindowPatternCardView wp, SetUpInformationUnit unit){
        inputOutputManager.print(draft.diceDraftToString());
        inputOutputManager.print(wp.wpToString());
        unit.setSourceIndex(this.choseDraftDie(draft));
        unit.setDestinationIndex(this.choseCellWp());
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
    
    public void addOnWp(WindowPatternCardView wp, SetUpInformationUnit unit){
        wp.getGlassWindow()[unit.getDestinationIndex()/WindowPatternCardView.MAX_COL][unit.getDestinationIndex() % WindowPatternCardView.MAX_COL].setDie(new DieView(unit.getColor(), unit.getValue()));
    }

    public void removeOnWp(WindowPatternCardView wp, SetUpInformationUnit unit){
        wp.getGlassWindow()[unit.getSourceIndex()/WindowPatternCardView.MAX_COL][unit.getSourceIndex() % WindowPatternCardView.MAX_COL].removeDie();
    }

    public void addOnDraft(DieDraftPoolView draft, SetUpInformationUnit unit){
        draft.getDice().add(new DieView(unit.getColor(), unit.getValue()));
    }

    public void removeOnDraft(DieDraftPoolView draft, SetUpInformationUnit unit){
        draft.getDice().remove(unit.getSourceIndex());
    }

    public void addOnRoundTrack(RoundTrackView roundTrack, SetUpInformationUnit unit){
        roundTrack.getAvailableDice().get(unit.getDestinationIndex()).add(new DieView(unit.getColor(), unit.getValue()));
    }

    public void removeOnRoundTrack(RoundTrackView roundTrack, SetUpInformationUnit unit){
        roundTrack.getAvailableDice().get(unit.getSourceIndex()).remove(unit.getOffset());
    }

}
