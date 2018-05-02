package it.polimi.ingsw.model;

/**
 * General interface for score computation.
 */
public interface IScore {

    /**
     * Computes a player's final score.
     * @param player whose score is to be computed.
     * @return the final score.
     */
    public int computeFinalScore(Player player);
}
