package it.polimi.ingsw.model;

import java.util.List;

/**
 * Interface representing what each player can do.
 */
public interface IPlayer {

    /**
     * Lets the player choose a WindowPatterCard.
     * @param availableWindows list of the possible WindowPatternCards among which the player can choose.
     */
    public void chooseWindowPatternCard(List<WindowPatternCard> availableWindows);
}
