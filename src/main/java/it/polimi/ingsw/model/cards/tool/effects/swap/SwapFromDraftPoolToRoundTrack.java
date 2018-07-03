package it.polimi.ingsw.model.cards.tool.effects.swap;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;
import it.polimi.ingsw.model.die.containers.RoundTrack;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.move.AMove;
import it.polimi.ingsw.model.move.DefaultDiePlacementMove;

/**
 * This class manages the swap effect between a die chosen from the draft pool container and a die
 * chosen from the round track container.
 */
public class SwapFromDraftPoolToRoundTrack extends ASwapDieEffect {

    /**
     * This method executes the swap and then, if possible, places the die in the window pattern card according to
     * user's coordinates.
     * @param manager the controller.
     * @param informationUnit the info to use.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit informationUnit) {

        RoundTrack roundTrack = manager.getControllerMaster().getCommonBoard().getRoundTrack();
        roundTrack.createCopy();

        DiceDraftPool draftPool = manager.getControllerMaster().getCommonBoard().getDraftPool();
        draftPool.createCopy();

        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
        wp.createCopy();

        Cell desiredCell = new Cell(informationUnit.getDestinationIndex() / WindowPatternCard.MAX_COL,
                informationUnit.getDestinationIndex() % WindowPatternCard.MAX_COL);

        Die die1 = draftPool.getAvailableDiceCopy().get(informationUnit.getSourceIndex());
        Die die2 = roundTrack.getAvailableDiceCopy().get(informationUnit.getExtraParam()).get(informationUnit.getOffset());


        if (!isRoundLegal(manager, informationUnit, roundTrack)) {
            manager.setMoveLegal(false);
            return;
        }

        if (!wp.canBePlaced(die2, desiredCell, wp.getGlassWindowCopy())) {
            if (checkExistingCellsToUse(wp, die2)) {
                manager.setMoveLegal(false);
                manager.sendNotificationToCurrentPlayer(wp.getErrorMessage() + COMMANDS_HELP);
                return;
            } else {
                super.swapDice(die1, die2);

                SetUpInformationUnit draftInfoUnit = new SetUpInformationUnit();
                SetUpInformationUnit roundTrackInfoUnit = new SetUpInformationUnit();

                packDraftInfoUnit(informationUnit, die1, draftInfoUnit);
                packRoundTrackInfoUnit(informationUnit, die2, roundTrackInfoUnit);

                manager.showDraftPoolRoundTrackSwap(draftInfoUnit, roundTrackInfoUnit);
            }
        }


            AMove defaultPlacement = new DefaultDiePlacementMove();
            defaultPlacement.executeMove(manager, informationUnit);

    }

        private boolean isRoundLegal(GamePlayManager manager, SetUpInformationUnit informationUnit, RoundTrack roundTrack){

            if(manager.getControllerMaster().getGameState().getActualRound() == 1) {
                manager.sendNotificationToCurrentPlayer("Il Tracciato dei Round è vuoto!"+ COMMANDS_HELP);
                return false;
            }

            if(roundTrack.getAvailableDiceCopy().get(informationUnit.getExtraParam()).isEmpty()) {
                manager.sendNotificationToCurrentPlayer("Non ci sono dadi nel round selezionato!" + COMMANDS_HELP);
                return false;
            }

            return true;
        }

        private void packRoundTrackInfoUnit(SetUpInformationUnit source, Die die, SetUpInformationUnit toFill){
            toFill.setSourceIndex(source.getExtraParam());
            toFill.setOffset(source.getOffset());
            toFill.setDestinationIndex(source.getExtraParam());
            toFill.setValue(die.getActualDieValue());
            toFill.setColor(die.getDieColor());
        }

        private void packDraftInfoUnit(SetUpInformationUnit source, Die die, SetUpInformationUnit toFill){
            toFill.setSourceIndex(source.getSourceIndex());
            toFill.setValue(die.getActualDieValue());
            toFill.setColor(die.getDieColor());
        }

}