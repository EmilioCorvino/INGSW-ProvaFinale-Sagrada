package it.polimi.ingsw.client.view.cli.managers.state;

import it.polimi.ingsw.client.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.client.view.cli.die.DieView;
import it.polimi.ingsw.client.view.cli.die.RoundTrackView;
import it.polimi.ingsw.client.view.cli.die.WindowPatternCardView;
import it.polimi.ingsw.client.view.cli.managers.general.InputOutputManager;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;

/**
 * This class manages all the interaction during the game play state.
 */
public class GamePlayManager{

    /**
     * Reference to the class that allows the communication with the server.
     */
    private InputOutputManager inputOutputManager;

    /**
     * Used to store information needed between calls of different methods from the server.
     */
    private SetUpInformationUnit extraInfo;

    public GamePlayManager(InputOutputManager inputOutputManager){
        this.inputOutputManager = inputOutputManager;
        this.extraInfo = new SetUpInformationUnit();
    }

    /**
     * This method ask the index of the die chosen in the draft, verifying if it is an int and if it is in bound.
     * @param draftPoolView The draft Pool
     * @return the index chosen.
     */
    public int choseDraftDie(DieDraftPoolView draftPoolView){

        int index = inputOutputManager.askInt("Inserisci l'indice del dado desiderato (da 0 a "+(draftPoolView.getDice().size()-1)+"): ");

        while(index < 0 || index> draftPoolView.getDice().size()-1) {
            index = inputOutputManager.askInt("Errore: inserire un valore tra (0-" + (draftPoolView.getDice().size() - 1) + "): ");
        }
        return index;
    }

    /**
     * This method ask to the user the row and the column of the cell where he want to place the die, and verifying if it is an int and if it is in bound.
     * @return the modular conversion of raw and column.
     */
    public int choseCellWp(){
        inputOutputManager.print("Inserisci le coordinate della cella desiderata.");

        int row = inputOutputManager.askInt("Riga: ");

        while (row < 0 || row > WindowPatternCardView.MAX_ROW - 1) {
            row = inputOutputManager.askInt("Errore: Valore non supportato, inserire un valore tra (0-" + (WindowPatternCardView.MAX_ROW - 1) + "): ");
        }

        int col = inputOutputManager.askInt("Colonna: ");

        while (col < 0 || col > WindowPatternCardView.MAX_COL - 1) {
            col = inputOutputManager.askInt("Errore: Valore non supportato, inserire un valore tra (0-" + (WindowPatternCardView.MAX_COL - 1) + "): ");

        }
        return row * WindowPatternCardView.MAX_COL + col;
    }

    /**
     * This method fill a set up info unit with the information of the position of the die chosen in the round track
     * @param roundTrack an instance of the round track
     * @param informationUnit the information unit to fill.
     */
    public void choseRoundDie(RoundTrackView roundTrack, SetUpInformationUnit informationUnit){

        int idRound = (inputOutputManager.askInt("Inserisci il numero del round in cui c'e' il dado desiderato: ")-1);
        int maxRoundSize;

        while(idRound < 0 || idRound > 9) {
            idRound = (inputOutputManager.askInt("Errore: Round non presente, inserire valore tra (1-10) ")-1);
        }
        informationUnit.setExtraParam(idRound);

        if (roundTrack.getAvailableDice().get(idRound).isEmpty())
            maxRoundSize = 0;
        else
            maxRoundSize = roundTrack.getAvailableDice().get(idRound).size()-1;

        int idDie = inputOutputManager.askInt("Inserisci la posizione del dado desiderato (0-"+ maxRoundSize +"): ");

        while(idDie < 0 || idDie > maxRoundSize) {
            idDie = inputOutputManager.askInt("Errore: indice errato, inserire indice tra (0-" + maxRoundSize + "): ");
        }

        informationUnit.setOffset(idDie);

    }

    /**
     * This method ask all the information needed for a default placement
     * @param draft the draft pool of the common board.
     * @param wp the wp of the player connected.
     * @param unit the unit that needs to be filled with the placement information and that needs to be sent to the controller.
     */
    public void getPlacementInfo(DieDraftPoolView draft, WindowPatternCardView wp, SetUpInformationUnit unit){
        inputOutputManager.print(draft.diceDraftToString());
        inputOutputManager.print(wp.wpToString());
        unit.setSourceIndex(this.choseDraftDie(draft));
        unit.setDestinationIndex(this.choseCellWp());
    }

    /**
     * Adds a die to the Window Pattern Card of a player. Modular arithmetic is used to compute the
     * index.
     * @param wp the wp of the player connected.
     * @param unit the unit that needs to be filled with the placement information and that needs to be sent to the controller.
     */
    public void addOnWp(WindowPatternCardView wp, SetUpInformationUnit unit){
        wp.getGlassWindow()[unit.getDestinationIndex()/WindowPatternCardView.MAX_COL][unit.getDestinationIndex() % WindowPatternCardView.MAX_COL].setDie(new DieView(unit.getColor(), unit.getValue()));
    }

    /**
     * Removes a die to the Window Pattern Card of a player. Modular arithmetic is used to compute the
     * index.
     * @param wp the wp of the player connected.
     * @param unit the unit that needs to be filled with the placement information and that needs to be sent to the controller.
     */
    public void removeOnWp(WindowPatternCardView wp, SetUpInformationUnit unit){
        wp.getGlassWindow()[unit.getSourceIndex()/WindowPatternCardView.MAX_COL][unit.getSourceIndex() % WindowPatternCardView.MAX_COL].removeDie();
    }

    /**
     * Adds a die to the Dice Draft Pool of a player.
     * @param draft the Dice Draft Pool of the player connected.
     * @param unit the unit that needs to be filled with the placement information and that needs to be sent to the controller.
     */
    public void addOnDraft(DieDraftPoolView draft, SetUpInformationUnit unit){
        draft.getDice().add(new DieView(unit.getColor(), unit.getValue()));
    }

    /**
     * Removes a die to the Dice Draft Pool of a player.
     * @param draft the Dice Draft Pool of the player connected.
     * @param unit the unit that needs to be filled with the placement information and that needs to be sent to the controller.
     */
    public void removeOnDraft(DieDraftPoolView draft, SetUpInformationUnit unit){
        draft.getDice().remove(unit.getSourceIndex());
    }

    /**
     * Adds a die to the Round Track of a player.
     * @param roundTrack the Dice Draft Pool of the player connected.
     * @param unit the unit that needs to be filled with the placement information and that needs to be sent to the controller.
     */
    public void addOnRoundTrack(RoundTrackView roundTrack, SetUpInformationUnit unit){
        roundTrack.getAvailableDice().get(unit.getDestinationIndex()).add(new DieView(unit.getColor(), unit.getValue()));
    }

    /**
     * Removes a die to the Round Track of a player.
     * @param roundTrack the Dice Draft Pool of the player connected.
     * @param unit the unit that needs to be filled with the placement information and that needs to be sent to the controller.
     */
    public void removeOnRoundTrack(RoundTrackView roundTrack, SetUpInformationUnit unit){
        roundTrack.getAvailableDice().get(unit.getSourceIndex()).remove(unit.getOffset());
    }

    /**
     * This method is used in particular tool (6, 11) to show the new die after the extraction.
     * @param infoUnit the container of all the info of the new die extracted.
     */
    public void showDie(SetUpInformationUnit infoUnit){
        setExtraInfo(infoUnit);
        if (infoUnit.getValue() != 0)
            inputOutputManager.print("Il dado rilanciato e': " + (new DieView(infoUnit.getColor(), infoUnit.getValue())).toStringDie());
        else
            inputOutputManager.print("Il dado estratto e' di colore: \033[" + infoUnit.getColor().getColorNumber() + "m" + infoUnit.getColor().getId() + "\033[0m");
    }

    public SetUpInformationUnit getExtraInfo() {
        return extraInfo;
    }

    private void setExtraInfo(SetUpInformationUnit extraInfo) {
        this.extraInfo = extraInfo;
    }
}
