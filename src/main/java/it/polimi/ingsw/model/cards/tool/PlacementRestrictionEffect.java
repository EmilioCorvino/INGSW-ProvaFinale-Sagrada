package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;

/**
 *
 */
public class PlacementRestrictionEffect extends AToolCardEffect {

    /**
     * This method executes the
     * @param manager
     * @param setUpInfoUnit
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {
        /*Die chosenDie = new Die(setUpInfoUnit.getValue(), setUpInfoUnit.getColor());
        WindowPatternCard playerWp = manager.getPlayerList().get(manager.getCurrentPlayerTurnIndex()).getWindowPatternCard();

        if(!playerWp.isContained(chosenDie)) {
            manager.showNotification("il dado non è contenuto in in questa mappa. Scegliere un dado valido.");
            manager.givePlayerObjectTofill();
            return;
        }

        playerWp.update(chosenDie);
        manager.showPlacementResult(manager.getPlayerList().get(manager.getCurrentPlayerTurnIndex()), setUpInfoUnit);*/
    }
}