package it.polimi.ingsw.model.cards.tool.PlacementEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ColorPlacementRestrictionEffect extends PlacementRestrictionEffect {

    /**
     * @param manager the controller.
     * @param setUpInformationUnit*/
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInformationUnit) {

        List<ArrayList<Die>> draftPoolDice = manager.getControllerMaster().getCommonBoard().getRoundTrack().getAvailableDice();
        Die chosenDie = draftPoolDice.get(setUpInformationUnit.getSourceIndex()).get(setUpInformationUnit.getOffset());

        if(setUpInformationUnit.getColor().equals(chosenDie.getDieColor()))
            super.executeMove(manager, setUpInformationUnit);
        else {
            manager.sendNotification("Il dado della mappa scelto deve essere dello stesso colore del dado scelto dalla round track");
        }

    }
}
