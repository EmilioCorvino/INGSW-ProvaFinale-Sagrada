package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.GamePlayManager;
import it.polimi.ingsw.controller.IOController;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.die.Die;

/**
 * This class manages the choice of a die by a player.
 */
public class ChooseDieMove implements IMove {

    private Die chosenDie;

    public ChooseDieMove() {

    }

    public ChooseDieMove(Die chosenDie) {
        setChosenDie(chosenDie);
    }

    public Die getChosenDie() {
        return chosenDie;
    }

    public void setChosenDie(Die chosenDie) {
        this.chosenDie = chosenDie;
    }

    @Override
    public void executeMove(CommonBoard commonBoard, IOController ioController) {

    }

    @Override
    public void executeMove(GamePlayManager commonBoard, SetUpInformationUnit setUpInfoUnit) {

    }
}
