package it.polimi.ingsw.model.cards.tool.effects.movement;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the effect that allows a player to move two dice in his {@link WindowPatternCard} which are of
 * the same color as a die on the {@link it.polimi.ingsw.model.die.containers.RoundTrack}.
 */
public class ColorBoundMoveWithRestrictionEffect extends MoveWithRestrictionsEffect {

    /**
     * This method executes the movement of a die in the {@link WindowPatternCard} and checks if the die has the same
     * color as the die on the round track that has been chosen.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInfoUnit object containing all the information needed to perform the move.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        List<ArrayList<Die>> roundTrack = manager.getControllerMaster().getCommonBoard().getRoundTrack().getAvailableDice();
        if (roundTrack.get(setUpInfoUnit.getExtraParam()).isEmpty()) {
            manager.setMoveLegal(false);
            manager.incrementEffectCounter();
            manager.sendNotificationToCurrentPlayer("La casella del Tracciato dei Round selezionata è vuota!"
                    + COMMANDS_HELP);
        } else {
            Die chosenRoundTrackDie = roundTrack.get(setUpInfoUnit.getExtraParam()).get(setUpInfoUnit.getOffset());
            WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
            Cell[][] glassWindowToConsider = manager.getEffectCounter() == 0 ? wp.getGlassWindow() : wp.getGlassWindowCopy();

            if(!checkMoveAvailability(glassWindowToConsider, setUpInfoUnit, manager.getEffectCounter() + 1)) {
                manager.setMoveLegal(false);
                manager.incrementEffectCounter();
                manager.sendNotificationToCurrentPlayer(this.invalidMoveMessage);
                manager.showRearrangementResult(manager.getControllerMaster().getGameState().getCurrentPlayer(), setUpInfoUnit);
                return;
            }

            if (glassWindowToConsider[setUpInfoUnit.getSourceIndex() / WindowPatternCard.MAX_COL]
                    [setUpInfoUnit.getSourceIndex() % WindowPatternCard.MAX_COL].getContainedDie().getDieColor()
                    .equals(chosenRoundTrackDie.getDieColor())) {
                super.executeMove(manager, setUpInfoUnit);
            } else {
                manager.setMoveLegal(false);
                manager.incrementEffectCounter();
                manager.sendNotificationToCurrentPlayer("Il dado della mappa scelto deve essere dello stesso colore " +
                        "del dado selezionato dal Tracciato dei Round!" + COMMANDS_HELP);
                manager.showRearrangementResult(manager.getControllerMaster().getGameState().getCurrentPlayer(), setUpInfoUnit);
            }
        }
    }
}
