package it.polimi.ingsw.model.ToolCards;

public class ToolCard implements IToolCard {

    private int availableTurn;

    /**
     * Checks if the available turn in which the tool card can be used corresponds to the current turn.
     * @param currentTurn: the turn used to compare with the turn available for the tool card to be used.
     * @return true if the current turn is equal to the turn in which the card can be used.
     */
    public boolean canBeUsed(int currentTurn) {
        return this.availableTurn == currentTurn || this.availableTurn == 2;
    }

    public int getAvailableTurn() {
        return availableTurn;
    }

    public void setAvailableTurn(int availableTurn) {
        this.availableTurn = availableTurn;
    }
}
