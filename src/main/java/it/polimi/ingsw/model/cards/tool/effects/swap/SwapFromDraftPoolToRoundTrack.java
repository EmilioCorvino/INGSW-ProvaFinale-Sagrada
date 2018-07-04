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

        manager.incrementEffectCounter();

        SetUpInformationUnit draftInfoUnit = new SetUpInformationUnit();
        SetUpInformationUnit roundTrackInfoUnit = new SetUpInformationUnit();

        RoundTrack roundTrack = manager.getControllerMaster().getCommonBoard().getRoundTrack();
        roundTrack.createCopy();

        DiceDraftPool draftPool = manager.getControllerMaster().getCommonBoard().getDraftPool();
        draftPool.createCopy();

        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
        wp.createCopy();

        Cell desiredCell = new Cell(informationUnit.getDestinationIndex() / WindowPatternCard.MAX_COL,
                informationUnit.getDestinationIndex() % WindowPatternCard.MAX_COL);

        if (!isRoundLegal(manager, informationUnit, roundTrack)) {
            manager.setMoveLegal(false);
            return;
        }
        Die rtDie = roundTrack.getAvailableDiceCopy().get(informationUnit.getExtraParam()).get(informationUnit.getOffset());

        if (!wp.canBePlaced(rtDie, desiredCell, wp.getGlassWindowCopy())) {
            if(checkExistingCellsToUse(wp, rtDie)) {
                manager.setMoveLegal(false);
                manager.sendNotificationToCurrentPlayer(wp.getErrorMessage() + COMMANDS_HELP);
                return;
            }else{
                manager.sendNotificationToCurrentPlayer("Non era possibile piazzare il dado in nessuna posizione.");
                manager.getControllerMaster().getGameState().getCurrentTurn().setDiePlaced(false);
                makeSwap(manager, draftPool, roundTrack, draftInfoUnit, roundTrackInfoUnit, informationUnit);
                return;
            }
        }


        makeSwap(manager, draftPool, roundTrack, draftInfoUnit, roundTrackInfoUnit, informationUnit);

        informationUnit.setSourceIndex(draftPool.getAvailableDiceCopy().size()-1);
        AMove defaultPlacement = new DefaultDiePlacementMove();
        defaultPlacement.executeMove(manager, informationUnit);

    }

    /**
     * This method verify if the round chosen is legal or not.
     * @param manager A part of controller needed for the interaction.
     * @param informationUnit The object which contains all the information of the input chosen by the user.
     * @param roundTrack The round Track
     * @return True if the round is legal, false if the round track is empty or if the round chosen is empty.
     */
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

    /**
     * This method pack the information unit to refresh the round track of the view.
     * @param source The object filled by the view that contains all the information token from the user.
     * @param die The die changed.
     * @param toFill The object to fill to refresh the roundTrack on the view.
     */
    private void packRoundTrackInfoUnit(SetUpInformationUnit source, Die die, SetUpInformationUnit toFill){
        toFill.setSourceIndex(source.getExtraParam());
        toFill.setOffset(source.getOffset());
        toFill.setDestinationIndex(source.getExtraParam());
        toFill.setValue(die.getActualDieValue());
        toFill.setColor(die.getDieColor());
    }

    /**
     * This method pack the information unit to refresh the draft of the view.
     * @param source The object filled by the view that contains all the information token from the user.
     * @param die The die changed.
     * @param toFill The object to fill to refresh the draft on the view.
     */
    private void packDraftInfoUnit(SetUpInformationUnit source, Die die, SetUpInformationUnit toFill){
        toFill.setSourceIndex(source.getSourceIndex());
        toFill.setValue(die.getActualDieValue());
        toFill.setColor(die.getDieColor());
    }

    /**
     * This method swap the dice from the round track to the draft, and call the controller to refresh the view.
     * @param manager A part of controller needed for the interaction.
     * @param draft The draft pool.
     * @param roundTrack The round track.
     * @param draftInfoUnit The information unit to fill for the draft's view refreshing
     * @param roundTrackInfoUnit The information unit to fill for the round Track's view refreshing
     * @param informationUnit The object which contains all the information of the input chosen by the user.
     */
    private void makeSwap(GamePlayManager manager, DiceDraftPool draft, RoundTrack roundTrack,
                          SetUpInformationUnit draftInfoUnit, SetUpInformationUnit roundTrackInfoUnit, SetUpInformationUnit informationUnit){
        //move die from round track to draft.
        roundTrack.setRoundToBeUpdated(informationUnit.getExtraParam());
        Die draftDie = roundTrack.removeDieFromCopy(informationUnit.getOffset());
        draft.addDieToCopy(draftDie);

        //move die from draft to roundTrack.
        Die rtDie = draft.removeDieFromCopy(informationUnit.getSourceIndex());
        roundTrack.setRoundToBeUpdated(informationUnit.getExtraParam());
        roundTrack.addDieToCopy(rtDie);

        packDraftInfoUnit(informationUnit, draftDie, draftInfoUnit);
        packRoundTrackInfoUnit(informationUnit, rtDie, roundTrackInfoUnit);

        manager.setMoveLegal(true);
        manager.showDraftPoolRoundTrackSwap(draftInfoUnit, roundTrackInfoUnit);
    }
}
