package it.polimi.ingsw.server.model.move;

import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.server.controller.managers.GamePlayManager;
import it.polimi.ingsw.server.model.die.Cell;
import it.polimi.ingsw.server.model.die.Die;
import it.polimi.ingsw.server.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.server.model.player.Player;

/**
 * This class manages a particular type of die placement. It differs from {@link DefaultDiePlacementMove} because the
 * die is not chosen from the {@link it.polimi.ingsw.server.model.die.containers.DiceDraftPool}, but it's given by a
 * previous {@link it.polimi.ingsw.server.model.cards.tool.effects.AToolCardEffect}. If said die cannot be placed, it is
 * handled with different policies.
 * @see it.polimi.ingsw.server.model.cards.tool.effects.swap.SwapFromDraftPoolToDiceBag
 * @see it.polimi.ingsw.server.model.cards.tool.effects.draft.DraftValueEffect
 */
public class RestrictedDiePlacementMove extends AMove {

    /**
     * This method executed the move considering some specific conditions.
     * @param manager the controller.
     * @param setUpInfoUnit the info to consider.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        Player player = manager.getControllerMaster().getGameState().getCurrentPlayer();
        WindowPatternCard wp = player.getWindowPatternCard();
        wp.createCopy();
        Die dieToCheck = new Die(setUpInfoUnit.getValue(), setUpInfoUnit.getColor());
        Cell cell = new Cell(setUpInfoUnit.getDestinationIndex() / WindowPatternCard.getMaxCol(),
                setUpInfoUnit.getDestinationIndex() % WindowPatternCard.getMaxCol());


        if (wp.canBePlaced(dieToCheck, cell, wp.getGlassWindowCopy())) {
            wp.setDesiredCell(cell);
            wp.addDieToCopy(dieToCheck);
            manager.setMoveLegal(true);
            manager.showPlacementResult(player, setUpInfoUnit);
        } else {
            if(!checkExistingCellsToUse(wp, dieToCheck)) {
                manager.setMoveLegal(true);
                manager.getControllerMaster().getGameState().getCurrentTurn().decrementDieCount();
                manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDiceCopy().add(dieToCheck);
                manager.getControllerMaster().getGameState().getCurrentTurn().setDiePlaced(false);
                manager.showUpdatedDraft(packMultipleInformation(manager));
            } else {
                manager.setMoveLegal(false);
            }
        }
    }
}
