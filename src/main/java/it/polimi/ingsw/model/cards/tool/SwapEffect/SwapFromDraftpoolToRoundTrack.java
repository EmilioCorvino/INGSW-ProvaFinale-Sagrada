package it.polimi.ingsw.model.cards.tool.SwapEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.RoundTrack;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the swap effect between a die chosen from the draft pool container and a die
 * chosen from the round track container.
 */
public class SwapFromDraftpoolToRoundTrack extends ASwapDieEffect {

    /**
     * This method executes the swap and then, if possible, places the die in the window pattern card according to
     * user's coordinates.
     * @param manager the controller.
     * @param informationUnit the info to use.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit informationUnit) {
        manager.setMoveLegal(true);

        Die die1 = manager.getControllerMaster().getCommonBoard().getDraftPool().removeDie(informationUnit.getSourceIndex());
        RoundTrack roundTrack = manager.getControllerMaster().getCommonBoard().getRoundTrack();
        roundTrack.setRoundToBeUpdated(informationUnit.getExtraParam());
        Die die2 = manager.getControllerMaster().getCommonBoard().getRoundTrack().removeDie(informationUnit.getOffset());

        super.swapDice(die1, die2);

        roundTrack.addDie(die2);
        manager.getControllerMaster().getCommonBoard().getDraftPool().addDie(die1);

        List<SetUpInformationUnit> list = packInformationToSend(die1, die2, informationUnit);
        manager.showDraftPoolRoundTrackSwap(list.get(0), list.get(1));
    }

    /**
     * This method constructs the information results to send to the controller.
     * @param die1 the first die.
     * @param die2 the second die.
     * @param info the information where to pick source and destination.
     * @return a list of setup containing proper information.
     */
    public List<SetUpInformationUnit> packInformationToSend(Die die1, Die die2, SetUpInformationUnit info) {
        List<SetUpInformationUnit> listToSend = new ArrayList<>();

        SetUpInformationUnit setup1 = new SetUpInformationUnit();
        setup1.setDestinationIndex(info.getSourceIndex());
        setup1.setValue(die1.getActualDieValue());
        setup1.setColor(die1.getDieColor());
        listToSend.add(setup1);

        SetUpInformationUnit setup2 = new SetUpInformationUnit();
        setup2.setDestinationIndex(info.getDestinationIndex());
        setup2.setValue(die2.getActualDieValue());
        setup2.setColor(die2.getDieColor());
        listToSend.add(setup2);

        return listToSend;
    }
}