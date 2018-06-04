package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.Die;

/**
 * This class manages the choice of a die by a player.
 */
public class ChooseDieMove implements IMove {

    private Die chosenDie;

    public ChooseDieMove() {
        chosenDie = new Die(0, Color.BLANK);
    }


    public Die getChosenDie() {
        return chosenDie;
    }

    public void setChosenDie(Die chosenDie) {
        this.chosenDie = chosenDie;
    }

    @Override
    public void executeMove(GamePlayManager commonBoard, SetUpInformationUnit setUpInfoUnit) {


    }
}
