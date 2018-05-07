package it.polimi.ingsw.model;

/**
 * General interface for score computation.
 */
public interface IScore {

    /**
     * Computes a player's final score.
     * @return the final score.
     */
    public int computeFinalScore();
}
