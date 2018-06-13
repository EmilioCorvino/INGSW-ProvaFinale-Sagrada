package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.exceptions.DieNotContainedException;

/**
 *
 */
public class SwapDieEffect extends AToolCardEffect {


    /**
     * TODO
     * @param manager
     * @param setUpInfoUnit
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        Player player = manager.getControllerMaster().getGameState().getCurrentPlayer();
        Die chosenDie = new Die(setUpInfoUnit.getValue(), setUpInfoUnit.getColor());

        //check if die is contained in source e destination

        Die roundDie = manager.getControllerMaster().getCommonBoard().
                getDraftPool().getAvailableDice().get(setUpInfoUnit.getIndex());

        setUpInfoUnit.setValue(roundDie.getActualDieValue());
        setUpInfoUnit.setColor(roundDie.getDieColor());

        //Check controls
        try {
            manager.getControllerMaster().getCommonBoard().getDraftPool().removeDie(roundDie);
        } catch (DieNotContainedException e) {
            e.printStackTrace();
        }
        manager.getControllerMaster().getCommonBoard().getRoundTrack().addDie(chosenDie);

    }
}
