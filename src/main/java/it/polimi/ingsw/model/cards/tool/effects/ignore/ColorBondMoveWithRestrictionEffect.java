package it.polimi.ingsw.model.cards.tool.effects.ignore;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ColorBondMoveWithRestrictionEffect extends MoveWithRestrictionsEffect {

    /**
     * This method execute a moving dice placement and control if the dice are the same of the die on the round track.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInformationUnit object containing all the information needed to perform the move.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInformationUnit) {

        List<ArrayList<Die>> roundTrack = manager.getControllerMaster().getCommonBoard().getRoundTrack().getAvailableDice();
        Die chosenDie = roundTrack.get(setUpInformationUnit.getSourceIndex()).get(setUpInformationUnit.getOffset());

        if(setUpInformationUnit.getColor().equals(chosenDie.getDieColor()))
            super.executeMove(manager, setUpInformationUnit);
        else {
            manager.sendNotificationToCurrentPlayer("Il dado della mappa scelto deve essere dello stesso colore del dado scelto dalla round track");
        }

    }
}
