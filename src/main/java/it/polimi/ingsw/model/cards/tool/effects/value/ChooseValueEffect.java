package it.polimi.ingsw.model.cards.tool.effects.value;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

/**
 * This class manages the effect of those tool cards that allow the player to increase the value of a chosen die.
 */
public class ChooseValueEffect extends AValueEffect {

    /**
     * This is a flag that indicates the die value needs to be increased.
     */
    private static final int INCREASE_CODE = 0;

    /**
     * This is a flag that indicates the die value needs to be decreased.
     */
    private static final int DECREASE_CODE = 1;

    /**
     * Constructs this effect with the default value.
     */
    public ChooseValueEffect() {
        super();
    }

    /**
     * Constructs this effect with a specific value.
     * @param offset: the value to set.
     */
    public ChooseValueEffect(int offset) {
        super(offset);
    }

    /**
     * This method increases the original die value by a factor of one.
     * @param die: the die on which to perform the action.
     * @return the die with the increased value or with the original value in case the checks fail.
     */
    public Die increaseDieValue(Die die) {
        int newValue = die.getActualDieValue() + 1;
        if(super.checkNewValue(newValue, die.getOriginalDieValue()) && super.checkValue(newValue)) {
            die.setActualDieValue(newValue);
            return die;
        }
        return die;
    }

    /**
     * This method decreases the original die value by a factor of one.
     * @param chosenDie the die on which to perform the action.
     * @return the die with the decreased value or with the original value in case the checks fail.
     */
    public Die decreaseDieValue(Die chosenDie) {
        int newValue = chosenDie.getActualDieValue() - 1;
        if(super.checkNewValue(newValue, chosenDie.getOriginalDieValue()) && super.checkValue(newValue)) {
            chosenDie.setActualDieValue(newValue);
            return chosenDie;
        }
        return chosenDie;
    }

    /**
     * This method executes the effect of the increment or decrement a chosen die.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInfoUnit object containing all the information needed to perform the move.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {
        DiceDraftPool draftPool = manager.getControllerMaster().getCommonBoard().getDraftPool();
        draftPool.createCopy();
        manager.incrementEffectCounter();

        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
        wp.createCopy();

        Die die = draftPool.getAvailableDiceCopy().get(setUpInfoUnit.getSourceIndex());

        if(die.getActualDieValue() == 6 && setUpInfoUnit.getExtraParam() == INCREASE_CODE) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer("Non puoi incrementare un 6 in un 1." + COMMANDS_HELP);
            return;
        }

        if(die.getActualDieValue() == 1 && setUpInfoUnit.getExtraParam() == DECREASE_CODE) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer("Non puoi decrementare un 1 in un 6." + COMMANDS_HELP);
            return;
        }

        if(setUpInfoUnit.getExtraParam() == INCREASE_CODE)
            die.setActualDieValue(increaseDieValue(die).getActualDieValue());

        if(setUpInfoUnit.getExtraParam() == DECREASE_CODE)
            die.setActualDieValue(decreaseDieValue(die).getActualDieValue());

        if(!super.checkExistingCellsToUse(wp, die)) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer("Non ci sono celle disponibili in cui il dado può essere piazzato."
                    + COMMANDS_HELP);
            return;
        }

        super.executeMove(manager, setUpInfoUnit);
    }
}
