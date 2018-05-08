package it.polimi.ingsw.model;

/**
 *
 */
public class DiePlacementMove implements IMove {

    /**
     *
     * @param chosenDie
     * @param destinationContainer
     */
    @Override
    public void executeMove(Die chosenDie, ADieContainer destinationContainer) {
        destinationContainer.update(chosenDie);
    }
}
