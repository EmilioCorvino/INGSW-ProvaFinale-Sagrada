package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.view.cli.InputOutputManager;
import it.polimi.ingsw.view.cli.die.ViewWindowPatternCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages all the interaction during the game's state of set up.
 */
public class SetUpGameCli {

    InputOutputManager inputOutputManager = new InputOutputManager();

    public int showMapsToChoose(List<SimplifiedWindowPatternCard> listWp){
        List<ViewWindowPatternCard> cards = new ArrayList<>();
        int idChosen;

        for (SimplifiedWindowPatternCard swp : listWp)
            cards.add(new ViewWindowPatternCard(swp));

        inputOutputManager.print("Scegli la WP desiderata tra: ");
        for (ViewWindowPatternCard wp : cards)
            wp.printWp();
        idChosen = this.getIdChosen();
        while (idChosen!= cards.get(0).getIdMap() || idChosen!=cards.get(1).getIdMap() || idChosen!=cards.get(2).getIdMap() || idChosen!=cards.get(3).getIdMap()){
            inputOutputManager.print("Id non presente!");
            idChosen = this.getIdChosen();
        }
        return idChosen;
    }

    public int getIdChosen(){
        return Integer.parseInt(inputOutputManager.askInformation("Inserire l'id della mappa scelta: "));
    }

    public void showInitializedBoard(CommonBoard board){}
}
