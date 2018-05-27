package it.polimi.ingsw.model.move;

import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.ADieContainer;

/**
 * This class manages the choice of a die by a player.
 */
public class ChooseDieMove {

    private Die chosenDie;

    public ChooseDieMove(Die chosenDie) {
        setChosenDie(chosenDie);
    }

    public Die getChosenDie() {
        return chosenDie;
    }

    public void setChosenDie(Die chosenDie) {
        this.chosenDie = chosenDie;
    }
}
