package it.polimi.ingsw.server.model;

/**
 * General interface for score computation.
 */
public interface IScore {

    /**
     * Computes a player's final score.
     */
    void computeTotalScore();
}
